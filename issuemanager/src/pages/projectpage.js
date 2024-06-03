import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './projectpage.css'; 
import Statistics from './Statistics'; 

function Projectinfo({ project, userId, userRole, memberId }) {
  const [showAccountInput, setShowAccountInput] = useState(false);
  const [selectedRole, setSelectedRole] = useState('');
  const [accountId, setAccountId] = useState('');
  const [issues, setIssues] = useState([]);
  const [selectedStatus, setSelectedStatus] = useState('');
  const [isStatisticsVisible, setIsStatisticsVisible] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    if (project) {
      fetchIssues();
    }
  }, [project, selectedStatus]);

  const fetchIssues = async () => {
    try {
      const response = await fetch(`/issues/projects/${project.id}`, {
        method: 'GET',
      });

      const data = await response.json();

      if (data.isSuccess) {
        const filteredIssues = selectedStatus
          ? data.result.issueList.filter(issue => issue.status === selectedStatus)
          : data.result.issueList;
        setIssues(filteredIssues);
      } else {
        console.error('Failed to fetch issues:', data.message);
      }
    } catch (error) {
      console.error('Error fetching issues:', error);
    }
  };

  const handleReportNewIssue = () => {
    navigate('/createissue', { 
      state: { projectId: project.id, userId, memberId, userRole } 
    });
  };

  const handleShowDetail = (issue) => {
    navigate('/editissue', { 
      state: { 
        projectId: project.id, 
        userId, 
        userRole, 
        memberId,
        issueData: issue 
      } 
    });
  };

  const handleRoleChange = (event) => {
    setSelectedRole(event.target.value);
  };

  const handleAccountIdChange = (event) => {
    setAccountId(event.target.value);
  };

  const handleAddAccount = async () => {
    if (!accountId.trim()) {
      alert("Please enter ID.");
      return;
    }
  
    const requestBody = {
      memberId: accountId 
    };
  
    console.log('Sending request:', JSON.stringify(requestBody));
  
    try {
      const response = await fetch(`/projects/${project.id}/members`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
      });
  
      if (!response.ok) {
        const errorData = await response.json();
        console.error('Error adding account:', errorData);
        alert(errorData.message || 'Failed to add account.');
        return;
      }
  
      const data = await response.json();
  
      if (data.isSuccess) {
        alert('Account added successfully!');
        setShowAccountInput(false);
        setSelectedRole('');
        setAccountId('');
      } else {
        alert(data.message || 'Failed to add account.');
      }
    } catch (error) {
      console.error('Error adding account:', error);
      alert('An error occurred while adding the account.');
    }
  };
  

  const handleCancel = () => {
    setShowAccountInput(false);
    setSelectedRole('');
    setAccountId('');
  };

  const handleStatusChange = (event) => {
    setSelectedStatus(event.target.value);
    setIsStatisticsVisible(false); 
  };

  const handleMyIssues = async () => {
    try {
      if (isStatisticsVisible) {
        setIsStatisticsVisible(false); 
      } else {
        let filteredIssues = [];

        if (userRole === 'ADMIN') {
          filteredIssues = issues;
        } else if (userRole === 'TESTER') {
          filteredIssues = issues.filter(issue => issue.reporter === memberId);
        } else if (userRole === 'PL') {
          filteredIssues = issues.filter(issue => issue.status === 'NEW' || issue.status === 'RESOLVED');
        } else if (userRole === 'DEV') {
          filteredIssues = issues.filter(issue => issue.assignee === memberId && issue.status === 'ASSIGNED');
        }

        setIssues(filteredIssues);
      }
    } catch (error) {
      console.error('Error fetching my issues:', error);
    }
  };

  const handleStatisticsClick = () => {
    setIsStatisticsVisible(true);
  };

  return (
    <div className="project-info-container">
      <div className="project-info">  
        {project ? (
          <>
            <h1>{project.name}</h1>
            {userRole === 'ADMIN' && (
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
            )}
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
              <button className="issue-button" onClick={handleMyIssues}>My Issues</button>
              <button className="issue-button" onClick={handleStatisticsClick}>Statistics</button>
              {userRole === 'TESTER' && (
                <button className="issue-create-button" onClick={handleReportNewIssue}>Report New Issue</button>
              )}
            </div>
            {isStatisticsVisible ? (
              <Statistics projectId={project.id} />
            ) : (
              <div className="issue-list">
                <div className="issue-header">
                  <div>Status</div>
                  <div>Priority</div>
                  <div>Title</div>
                  <div>Reporter</div>
                  <div>Assignee</div>
                  <div>Fixer</div>
                </div>
                {issues.length > 0 ? (
                  issues.map(issue => (
                    <div key={issue.issueId} className="issue-item" onClick={() => handleShowDetail(issue)}>
                      <div>[{issue.status}]</div>
                      <div>{issue.priority}</div>
                      <div>{issue.title}</div>
                      <div>{issue.reporter}</div>
                      <div>{issue.assignee}</div>
                      <div>{issue.fixer}</div>
                      <button className="issue-detail-button">Show Detail</button>
                    </div>
                  ))
                ) : (
                  <p>No issues found</p>
                )}
              </div>
            )}
          </>
        ) : (
          <h1>No project selected</h1>
        )}
      </div>
    </div>
  );
}

export default Projectinfo;
