import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './signup.css';

function SignUp() {
  // 선택된 역할 상태를 관리하는 useState 훅
  const [selectedRole, setSelectedRole] = useState('');

  // 체크박스 변경 핸들러
  const handleCheckboxChange = (value) => {
    setSelectedRole(value);
  };

  return (
    <div>
       <head>
            <title>Sign up</title>
        </head>
        <body>
            <div class="container">
            <div class="member-container">
                <div class="header">
                <div>WELCOME!</div>
                </div>
                <div class="user-info">
                <div class="user-info-id">
                    <div>* ID</div>
                    <input type="text" />
                </div>
                <div class="user-info-pw">
                    <div>* Password</div>
                    <input type="Password" />
                    <div class="user-info-pw-check">
                    <div>* Verify Password</div>
                    <input type="password" />
                    </div>
                </div>
                <div class="role-check">
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
                        <label for="admin_role">Administer</label>
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
                        <label for="pl_role">PL</label>
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
                        <label for="developer_role">Developer</label>
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
                        <label for="tester_role">Tester</label>
                    </li>
                    </ul>
                </div>
                <div class="btn">
                    <button>Sign Up</button>
                </div>
                <div class="signin-link">
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
