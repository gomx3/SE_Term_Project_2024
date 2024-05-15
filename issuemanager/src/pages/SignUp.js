import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import styles from './Signup.module.css';

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
            <div className={styles.container}>
            <div className={styles.member_container}>
                <div className={styles.header}>
                <div>WELCOME!</div>
                </div>
                <div className={styles.user_info}>
                <div className={styles.user_info_id}>
                    <div>* ID</div>
                    <input type="text" />
                </div>
                <div className={styles.user_info_pw}>
                    <div>* Password</div>
                    <input type="Password" />
                    <div className={styles.user_info_pw_check}>
                    <div>* Verify Password</div>
                    <input type="password" />
                    </div>
                </div>
                <div className={styles.role_check}>
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
                <div className={styles.btn}>
                    <button>Sign Up</button>
                </div>
                <div className={styles.signin_link}>
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
