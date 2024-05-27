import React, { useState, useEffect } from 'react';
import './projectpage.css'; // CSS 파일을 임포트합니다

const admin = "admin1";

const mockIssues = [
  {
    id: 1,
    reporterId: "reporter1",
    fixerId: "",
    assigneeId: "",
    status: "NEW",
    priority: "MAJOR",
    title: "Issue title 1"
  },
  {
    id: 2,
    reporterId: "reporter2",
    fixerId: "fixer1",
    assigneeId: "assignee1",
    status: "CLOSED",
    priority: "MINOR",
    title: "Issue title 2"
  },
  // 추가 이슈 데이터...
];

function Projectinfo({ project }) {
  const [showAccountInput, setShowAccountInput] = useState(false);
  const [selectedRole, setSelectedRole] = useState('');
  const [accountId, setAccountId] = useState('');
  const [issues, setIssues] = useState([]);
  const [selectedStatus, setSelectedStatus] = useState('');

  useEffect(() => {
    // 실제 API 호출이 있다면 이곳에서 수행합니다.
    // 임시로 하드코딩된 데이터를 사용합니다.
    setIssues(mockIssues);
  }, []);

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

  const handleStatusChange = (event) => {
    setSelectedStatus(event.target.value);
  };

  const filteredIssues = selectedStatus
    ? issues.filter(issue => issue.status === selectedStatus)
    : issues;

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
            <div className="issue-btn-container">
        <select className="status-select" value={selectedStatus} onChange={handleStatusChange}>
          <option value="">All Issues</option>
          <option value="NEW">NEW</option>
          <option value="ASSIGNED">ASSIGNED</option>
          <option value="FIXED">FIXED</option>
          <option value="RESOLVED">RESOLVED</option>
          <option value="CLOSED">CLOSED</option>
          <option value="REOPENED">REOPENED</option>
        </select>
        <button className="issue-button">My Issues</button>
        <button className="issue-button">Statistics</button>
        <button className="issue-create-button">Report New Issue</button>
      </div>
      <div className="issue-list">
        <div className="issue-header">
          <div>Status</div>
          <div>Priority</div>
          <div>Title</div>
          <div>Reporter</div>
          <div>Assignee</div>
          <div>Fixer</div>
        </div>
        {filteredIssues.length > 0 ? (
          filteredIssues.map(issue => (
            <div key={issue.id} className="issue-item">
              <div>[{issue.status}]</div>
              <div>{issue.priority}</div>
              <div>{issue.title}</div>
              <div>{issue.reporterId}</div>
              <div>{issue.assigneeId}</div>
              <div>{issue.fixerId}</div>
              <button className="issue-detail-button">Show Detail</button>
            </div>
          ))
        ) : (
          <p>No issues found</p>
        )}
      </div>
          </>
          
        ) : (
          <h1>No project selected</h1>
        )}
      </div>
      
    </div>
  );
}

export default Projectinfo;
