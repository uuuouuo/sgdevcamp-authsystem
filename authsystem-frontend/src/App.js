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
  const [users, setUsers] = useState([
    // {
    // id: "",
    // name: "",
    // },
  ]);

  /* 로그인 */
  const onClickLogin = (event) => {
    event.preventDefault();

    const body = { id: email, password: password };

    if (body.email === "" || body.password === "") {
      return;
    }

    axios
      .post("http://localhost:8080/user/login", body, config)
      .then((response) => {
        console.log(response.data);
        console.log(body.id, body.password);

        setCookie("token", response.data);
        setMode("READ");
        getUser();
      })
      .catch((error) => {
        console.log(error.response.data.message);
        alert(error.response.data.message);
      });
  };

  /* 회원가입 */
  const onClickJoin = (event) => {
    event.preventDefault();

    const body = { id: email, password: password, name: name };

    if (body.email === "" || body.password === "" || body.name === "") {
      return;
    }

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
        console.log(error.response.data.message);
        alert("사용중인 이메일입니다.");
      });
  };

  /* 유저 정보 받아오기 */
  const getUser = () => {
    axios
      .get("http://localhost:8080/user/list")
      .then((response) => {
        console.log(response.data);

        const _users = response.data.response.map((d) => ({
          id: d.id,
          name: d.name,
        }));

        setUsers(_users);
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
    content = (
      <div class="container" id="container">
        <div class="form-container sign-in-container">
          <form onSubmit={onClickLogin}>
            <h1>Sign In</h1>
            <input
              type="email"
              value={email}
              placeholder="Email"
              onChange={onChangeEmail}
            />
            <input
              type="password"
              value={password}
              placeholder="Password"
              onChange={onChangePassword}
            />
            <button formAction="">Sign In</button>
            <button
              onClick={() => {
                setMode("JOIN");
                setEmail("");
                setPassword("");
                setName("");
              }}
            >
              Sign Up
            </button>
          </form>
        </div>
      </div>
    );
  } else if (mode === "JOIN") {
    content = (
      <div class="container" id="container">
        <div class="form-container sign-up-container">
          <form onSubmit={onClickJoin}>
            <h1>Sign Up</h1>
            <input
              type="email"
              value={email}
              placeholder="이메일을 입력해 주세요."
              onChange={onChangeEmail}
            />
            <input
              type="name"
              value={name}
              placeholder="이름을 입력해 주세요."
              onChange={onChangeName}
            />
            <input
              type="password"
              value={password}
              placeholder="비밀번호를 입력해 주세요."
              onChange={onChangePassword}
            />
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
      </div>
    );
  } else if (mode === "READ") {
    content = (
      <>
        <div class="container" id="container">
          <h1
            style={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            User Table
          </h1>
          <table class="blueone"
          style={{
            width: "80%",
            justifyContent: "center",
            alignItems: "center",
          }}>
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
          </table>
        </div>
        <br />
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
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
          </button>
        </div>
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
