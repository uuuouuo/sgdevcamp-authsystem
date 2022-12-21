import React, { useState } from "react";
import { useCookies } from "react-cookie";
import axios from "axios";
import "./App.css";

function App() {
  const [mode, setMode] = useState("LOGIN");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const [cookie, setCookie] = useCookies(["token"]);
  const config = { "Content-Type": "application/json" };

  /* 로그인 */
  const onClickLogin = (event) => {
    event.preventDefault();

    const body = { id: email, password: password };

    axios
      .post("http://localhost:8080/user/login", body, config)
      .then((response) => {
        console.log("response.data", response.data);
        setCookie("token", response.data);
        setMode('READ')
      })
      .catch((error) => {
        console.log(error.response);
      });
  };

  /* 회원가입 */
  const onClickJoin = (event) => {
    event.preventDefault();

    const body = { id: email, password: password, name: name };

    axios
      .post("http://localhost:8080/user/join", body, config)
      .then((response) => {
        console.log("response.data", response.data);
        setMode('LOGIN');
        setEmail("");
        setPassword("");
        setName("");
      })
      .catch((error) => {
        console.log(error.response);
      });
  };

  const onChangeEmail = (event) => {
    setEmail(event.target.value);
  };
  const onChangePassword = (event) => {
    setPassword(event.target.value);
  };
  const onChangeName = (event) => {
    setName(event.target.value);
  };

  /* 페이지 모드 변환 */
  let content,
    header = null;

  if (mode === "LOGIN") {
    header = (
      <div
        name="header"
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        안녕하세요
      </div>
    );
    content = (
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          // width: '100%', height: '100vh'
        }}
      >
        <form
          style={{ display: "flex", flexDirection: "column" }}
          onSubmit={onClickLogin}
        >
          <label>Email </label>
          <input type="email" value={email} onChange={onChangeEmail} />
          <label>Password </label>
          <input type="password" value={password} onChange={onChangePassword} />
          <br />
          <button formAction="">Login</button>
          <button onClick={() => {
            setMode("JOIN");
            setEmail("");
            setPassword("");
            setName("");
          }}>Join</button>
        </form>
      </div>
    );
  }
  else if (mode === "JOIN") {
    header = (
      <div
        name="header"
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        회원가입
      </div>
    );
    content = (
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <form
          style={{ display: "flex", flexDirection: "column" }}
          onSubmit={onClickJoin}
        >
          <label>Email </label>
          <input
            type="email"
            value={email}
            placeholder="이메일 입력"
            onChange={onChangeEmail}
          />
          <label>Name </label>
          <input
            type="name"
            value={name}
            placeholder="이름 입력"
            onChange={onChangeName}
          />
          <label>Password </label>
          <input
            type="password"
            value={password}
            placeholder="비밀번호 입력"
            onChange={onChangePassword}
          />
          <br />
          <button formAction="">Join</button>
          <button onClick={() => {
            setMode("LOGIN");
            setEmail("");
            setPassword("");
            setName("");
          }}>Home</button>
        </form>
      </div>
    );
  }
  else if (mode === 'READ') {
    header = (
      <div
        name="header"
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        유저 관리
      </div>
    );
    content = (
      <button onClick={() => {
        setMode("LOGIN");
        setEmail("");
        setPassword("");
      }}>Home</button>
    )
  }

  return (
    <div>
      {header}
      {content}
    </div>
  );
}

export default App;
