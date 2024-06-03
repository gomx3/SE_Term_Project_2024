import React, { useState, useEffect } from 'react';
import { useLocation } from "react-router-dom";
import IssueInfo from './IssueInfo';
import IssueComment from './IssueComment';
import styles from './CreateIssue.module.css';

function CreateIssue() {
  const location = useLocation(); 


  const projectId = location.state.projectId;

  const [user, setUser] = useState({ 
    id: location.state.userId,
    memberId: location.state.memberId,
    role: location.state.userRole,
  });

  const [issue, setIssue] = useState({
    id: '',
    title: '',
    description: '',
    status: 'NEW',
    category: 'OTHERS',
    reporter: user.memberId, 
    reportedDate: '',
    priority: 'MAJOR',
    assignee: '',
    fixer:'',
  });

  const [comment, setComment] = useState({
    comments: [],
    content: '',
  })
  
  const categories = [
    'MEMORY_LEAK',
    'CRASH',
    'USER_FEEDBACK',
    'SECURITY',
    'OTHERS',
  ]

  function getLocalDateISOString() {
    const now = new Date();
    const timezoneOffset = now.getTimezoneOffset() * 60000;
    const localISOString = new Date(now - timezoneOffset).toISOString().split('T')[0];
    return localISOString;
  }

  useEffect(() => {
    const currentDate = getLocalDateISOString();
    setIssue((prevIssue) => ({
      ...prevIssue,
      reportedDate: currentDate,
    }));
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.editContainer}>
        <div>
          <h1 className={styles.h1}>CREATE ISSUE</h1>
        </div>
        <IssueInfo
          projectId={projectId}
          user={user}
          issue={issue}
          setIssue={setIssue}
          categories={categories}
        />
      </div>
      <div className={styles.commentContainer}>
        <div className={styles.userInfo}>
          <p>current user: {user.memberId}/{user.role}</p>
        </div>
        <IssueComment
          user={user}
          issue={issue}
          comment={comment}
          setComment={setComment}
        />
      </div>
    </div>
  );
}

export default CreateIssue;