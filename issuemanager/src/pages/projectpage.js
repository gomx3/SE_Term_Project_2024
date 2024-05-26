import React, { useState } from 'react';
import './projectpage.css'; // CSS 파일을 임포트합니다

const admin = "admin1";

function Projectinfo({ project }) {
  const [showAccountInput, setShowAccountInput] = useState(false);
  const [selectedRole, setSelectedRole] = useState('');
  const [accountId, setAccountId] = useState('');

  const handleRoleChange = (event) => {
    setSelectedRole(event.target.value);
  };

  const handleAccountIdChange = (event) => {
    setAccountId(event.target.value);
  };

  const handleAddAccount = () => {
    if (!accountId.trim() || !selectedRole.trim()) {
      alert("Please enter ID and select a role.");
      return;
    }
  
    // Add account logic here
    setShowAccountInput(false);
    setSelectedRole('');
    setAccountId('');
  };
  

  const handleCancel = () => {
    setShowAccountInput(false);
    setSelectedRole('');
    setAccountId('');
  };

  return (
    <div className="project-info-container">
      <div className="project-info">
        {project ? (
          <>
            <h1>{project}</h1>
            <p>Created by: {admin}</p>
            <div className="add-account-container">
              {!showAccountInput && (
                <button className="add-account-button" onClick={() => setShowAccountInput(true)}>Add New Account</button>
              )}
              {showAccountInput && (
                <div className="account-input-container">
                  <select value={selectedRole} onChange={handleRoleChange}>
                    <option value="">Select Role</option>
                    <option value="PL">PL</option>
                    <option value="Developer">Developer</option>
                    <option value="Tester">Tester</option>
                  </select>
                  <input 
                    type="text" 
                    value={accountId} 
                    onChange={handleAccountIdChange} 
                    placeholder="Enter ID"
                  />
                  <button className="account-cancel" onClick={handleCancel}>Cancel</button>
                  <button className="account-add" onClick={handleAddAccount}>Add</button>
                </div>
              )}
            </div>
          </>
        ) : (
          <h1>No project selected</h1>
        )}
      </div>
      <div className="issue-btn-container">
          <button className="issue-button">ALL Issues</button>
          <button className="issue-button">My Issues</button>
          <button className="issue-button">Statsics</button>
          <button className="issue-create-button">Report New Issue</button>
        </div>
      <div className='issue-list'>

      </div>
    </div>
  );
  
  
}

export default Projectinfo;
