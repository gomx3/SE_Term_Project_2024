import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './signup.css';
import SignIn from './SignIn';

function SignUp() {
  const [memberId, setMemberId] = useState('');
  const [password, setPassword] = useState('');
  const [verifyPassword, setVerifyPassword] = useState('');
  const [selectedRole, setSelectedRole] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate(); // useNavigate 훅을 사용하여 navigate 함수를 가져옴

  const handleCheckboxChange = (value) => {
    setSelectedRole(value);
  };

  const handleSignUp = async () => {
    if (!memberId.trim() || !password.trim() || !verifyPassword.trim() || !selectedRole.trim()) {
      setError('All fields are required.');
      return;
    }
    if (password !== verifyPassword) {
      setError('Passwords do not match.');
      return;
    }
    
    const roleMap = {
      admin: 'ADMIN',
      pl: 'PL',
      developer: 'DEV',
      tester: 'TESTER'
    };

    const requestBody = {
      memberId,
      pw: password,
      role: roleMap[selectedRole]
    };

    try {
      const response = await fetch('https://api.yourdomain.com/signup', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
      });
      
      const data = await response.json();
      
      if (data.isSuccess) {
        alert('Sign up successful!');
        // 회원가입 성공 시 로그인 페이지로 이동
        navigate('/signin');
      } else {
        setError(data.message || 'Sign up failed.');
      }
    } catch (error) {
      setError('An error occurred during sign up.');
    }
  };

  return (
    <div>
      <head>
        <title>Sign up</title>
      </head>
      <body>
        <div className="signup_container">
          <div className="member-container">
            <div className="signup_header">
              <div>WELCOME!</div>
            </div>
            <div className="user-info">
              <div className="user-info-id">
                <div>* ID</div>
                <input type="text" value={memberId} onChange={(e) => setMemberId(e.target.value)} />
              </div>
              <div className="user-info-pw">
                <div>* Password</div>
                <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                <div className="user-info-pw-check">
                  <div>* Verify Password</div>
                  <input type="password" value={verifyPassword} onChange={(e) => setVerifyPassword(e.target.value)} />
                </div>
              </div>
              <div className="role-check">
                <div>* Your Role</div>
                <ul>
                  <li>
                    <input
                      type="checkbox"
                      id="admin_role"
                      name="role"
                      value="admin"
                      checked={selectedRole === 'admin'}
                      onChange={() => handleCheckboxChange('admin')}
                    />
                    <label htmlFor="admin_role">Administer</label>
                  </li>
                  <li>
                    <input
                      type="checkbox"
                      id="pl_role"
                      name="role"
                      value="pl"
                      checked={selectedRole === 'pl'}
                      onChange={() => handleCheckboxChange('pl')}
                    />
                    <label htmlFor="pl_role">PL</label>
                  </li>
                  <li>
                    <input
                      type="checkbox"
                      id="developer_role"
                      name="role"
                      value="developer"
                      checked={selectedRole === 'developer'}
                      onChange={() => handleCheckboxChange('developer')}
                    />
                    <label htmlFor="developer_role">Developer</label>
                  </li>
                  <li>
                    <input
                      type="checkbox"
                      id="tester_role"
                      name="role"
                      value="tester"
                      checked={selectedRole === 'tester'}
                      onChange={() => handleCheckboxChange('tester')}
                    />
                    <label htmlFor="tester_role">Tester</label>
                  </li>
                </ul>
              </div>
              {error && <div className="error-message">{error}</div>}
              <div className="signup-btn">
                <button onClick={handleSignUp}>Sign Up</button>
              </div>
              <div className="signin-link">
                <p>Go back to <Link to="/signin">Sign In</Link></p>
              </div>
            </div>
          </div>
        </div>
      </body>
    </div>
  );
}

export default SignUp;
