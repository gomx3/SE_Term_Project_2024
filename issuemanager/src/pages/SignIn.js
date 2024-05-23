import React from 'react';
import { Link } from 'react-router-dom';
import './signin.css';

function SignIn() {
  return (
    <div>
      <html lang="English">
        <head>
          <title>Sign In</title>
        </head>
        <body>
          <div class="signin_container">
            <div class="member-container">
              <div class="signin_header">
                <div>WELCOME BACK!</div>
              </div>
              <div class="user-info">
                <div class="user-info-id">
                  <div>* ID</div>
                  <input type="text" />
                </div>
                <div class="user-info-pw">
                  <div>* PASSWORD</div>
                  <input type="password" />
                </div>
                <div class="btn">
                  <button>Sign In</button>
                </div>
                <div class="signup-link">
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
