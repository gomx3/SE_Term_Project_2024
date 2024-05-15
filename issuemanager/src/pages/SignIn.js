import React from 'react';
import { Link } from 'react-router-dom';
import styles from './Signin.module.css';

function SignIn() {
  return (
    <div>
      <html lang="English">
        <head>
          <title>Sign In</title>
        </head>
        <body>
          <div className={styles.container}>
            <div className={styles.member_container}>
            <div className={styles.header}>
                <div>WELCOME BACK!</div>
              </div>
              <div className={styles.user_info}>
                <div className={styles.user_info_id}>
                  <div>* ID</div>
                  <input type="text" />
                </div>
                <div className={styles.user_info_pw}>
                  <div>* PASSWORD</div>
                  <input type="password" />
                </div>
                <div className={styles.btn}>
                  <button>Sign In</button>
                </div>
                <div className={styles.signup_link}>
                  <p>
                    Don't have an account yet? <Link to="/signup">Sign Up</Link>
                  </p>
                </div>
              </div>
            </div>
          </div>
        </body>
      </html>
    </div>
  );
}

export default SignIn;
