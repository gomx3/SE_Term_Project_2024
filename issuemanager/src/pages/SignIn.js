import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './signin.css';

function SignIn() {
  const [memberId, setMemberId] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSignIn = async () => {
    setError('');

    if (!memberId.trim() || !password.trim()) {
      setError('Please enter both ID and password.');
      return;
    }

    const requestBody = {
      memberId,
      pw: password
    };

    try {
      const response = await fetch('/members/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
      });

      const data = await response.json();

      console.log('Response:', data);

      if (data.isSuccess) {
        alert('Sign in successful!');
        navigate('/', { state: { id: data.result.id, memberId: data.result.memberId, role: data.result.role } });
      } else {
        setError(data.message || 'Sign in failed.');
      }
    } catch (error) {
      console.error('Error:', error);
      setError('An error occurred during sign in.');
    }
  };

  return (
    <div>
      <html lang="English">
        <head>
          <title>Sign In</title>
        </head>
        <body>
          <div className="signin_container">
            <div className="member-container">
              <div className="signin_header">
                <div>WELCOME BACK!</div>
              </div>
              <div className="user-info">
                <div className="user-info-id">
                  <div>* ID</div>
                  <input type="text" value={memberId} onChange={(e) => setMemberId(e.target.value)} />
                </div>
                <div className="user-info-pw">
                  <div>* PASSWORD</div>
                  <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                </div>
                {error && <div className="error-message">{error}</div>}
                <div className="signon-btn">
                  <button onClick={handleSignIn}>Sign In</button>
                </div>
                <div className="signup-link">
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