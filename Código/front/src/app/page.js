'use client';
import Cookie from 'js-cookie';
import { useRouter } from 'next/navigation'

export default function Home() {

  const router = useRouter()


  const loginBackEnd = () => {
    try {
      console.log("loginBackEnd");
      fetch(`http://localhost:8080/login?username=${document.getElementById("email").value}&password=${document.getElementById("password").value}`, {
        method: "POST"
      }).then(response => {
        if (response.ok) {
          console.log("response.ok");
          response.json().then(data => {
            const expirationTime = Date.now() + 60 * 60 * 1000; // 1 hour in ms
            Cookie.set('token', data.token, { expires: 1/24 }); // Token expires in 1 hour
            Cookie.set('tokenExpiration', expirationTime);
            Cookie.set('userProfile', data.profile, { expires: 1/24 });
          });
        }
      }
      );

    }catch(e){
      console.log(e);
    }
    router.push("/Home");

  }

  return (
<div className="login-container">
  
        <h2>Login</h2>
            <div className="input-group">
                <label htmlFor="email">Email</label>
                <input type="email" id="email" required></input>
            </div>
            <div className="input-group">
                <label htmlFor="password">Senha</label>
                <input type="password" id="password" required></input>
            </div>
            <button  className="login-btn" onClick={loginBackEnd}>Login</button>
    </div>
  );
}
