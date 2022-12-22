import React, { useState } from "react";
import { useCookies } from "react-cookie";
import axios from "axios";
import "./App.css";
import { getSuggestedQuery } from "@testing-library/react";

function App() {
  const [mode, setMode] = useState("LOGIN");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const [cookie, setCookie] = useCookies(["token"]);
  const config = { "Content-Type": "application/json" };
  const [users, setUsers] = useState([
    {
      id: "",
      name: "",
    },
  ]);

  /* 로그인 */
  const onClickLogin = (event) => {
    event.preventDefault();

    const body = { id: email, password: password };

    axios
      .post("http://localhost:8080/user/login", body, config)
      .then((response) => {
        console.log("response.data", response.data);
        setCookie("token", response.data);
        setMode("READ");
        getUser();
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
        console.log(response.data);

        setMode("LOGIN");
        setEmail("");
        setPassword("");
        setName("");
      })
      .catch((error) => {
        console.log(error.response);
      });
  };

  /* 유저 정보 받아오기 */
  const getUser = () => {
    axios
      .get("http://localhost:8080/user/list")
      .then((response) => {
        console.log(response.data);

        const _users = response.data.map((d) => ({
          id: d.id,
          name: d.name,
        }));

        setUsers(users.concat(_users));
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

  let content,
    header = null;

  /* 페이지 모드 변환 */

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
          <button
            onClick={() => {
              setMode("JOIN");
              setEmail("");
              setPassword("");
              setName("");
            }}
          >
            Join
          </button>
        </form>
      </div>
    );
  } else if (mode === "JOIN") {
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
          <button
            onClick={() => {
              setMode("LOGIN");
              setEmail("");
              setPassword("");
              setName("");
            }}
          >
            Home
          </button>
        </form>
      </div>
    );
  } else if (mode === "READ") {
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
      <>
        <div style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}>
        <table>
          <thead>
          <tr>
              <th>id</th>
              <th>name</th>
          </tr>
        </thead>
        <tbody>
          {users.map(({ id, name }) => (
            <tr>
              <td>{id}</td>
              <td>{name}</td>
            </tr>
          ))}
        </tbody>
          </table></div>
        <br/>
        <div style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}>
        <button 
          onSubmit={onClickJoin}
          onClick={() => {
            setMode("LOGIN");
            setEmail("");
            setPassword("");
            setUsers([]);
          }}
        >
          Logout
          </button></div>
      </>
    );
  }

  return (
    <div>  
      {header}
      {content}
    </div>
  );
}

export default App;
