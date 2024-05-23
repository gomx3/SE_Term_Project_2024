import React, { useState, useEffect } from 'react';
import './EditIssue.css';

function EditIssue() {
  const [issue, setIssue] = useState({
    title: '',
    description: '',
    category: '',
    reporter: '',
    reportedDate: '',
    priority: '',
    assignee: '',
    fixer:'',
    comments: [],
    newComment: '',
  });

  const [userId, setUserId] = useState('A'); // 현재 사용자 id (A 또는 B, ...)

  useEffect(() => {
    // 컴포넌트가 처음 렌더링될 때 현재 날짜를 설정
    const currentDate = new Date().toISOString().split('T')[0];
    setIssue((prevIssue) => ({ 
      ...prevIssue, 
      reportedDate: currentDate,
     }));
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setIssue((prevIssue) => ({ ...prevIssue, [name]: value }));
  };

  const handleCategoryChange = (category) => {
    setIssue((prevIssue) => {
      if (prevIssue.category.includes(category)) {
        return {
          ...prevIssue,
          category: prevIssue.category.filter((item) => item !== category),
        };
      } else {
        return {
          ...prevIssue,
          category: [...prevIssue.category, category],
        };
      }
    });
  }

  const addComment = () => {
    setIssue((prevIssue) => ({
      ...prevIssue,
      comments: [...prevIssue.comments, prevIssue.newComment],
      newComment: '',
    }));
  };

  const handleSubmit = () => {
    // 이슈 편집 완료 처리
    console.log('Issue edited:', issue);
  };

  const categories = [
    'memory leak',
    'crash occurrence',
    'user feedback',
    'security vulnerability',
    'others',
  ];

  return (
    <div className="container">
      <h2>EDIT ISSUE</h2>
      <div className="edit-container">
        <div className="up">
          <label>Title</label>
          <input
            type="text"
            name="title"
            value={issue.title}
            onChange={handleChange}
            disabled={userId === 'A'} // A 사용자는 Title 수정 불가
          />
          <label>Description</label>
          <textarea
            name="description"
            value={issue.description}
            onChange={handleChange}
          />
          <label>Issue Category</label>
          <div>
            {categories.map((category) => (
              <div key={category} className="category-container">
                <input
                  type="radio"
                  id={category}
                  name="category"
                  value={category}
                  checked={issue.category.includes(category)}
                  onChange={() => handleCategoryChange(category)}
                />
                <label htmlFor={category}>{category}</label>
              </div>
            ))}
          </div>
        </div>
        <div className="down">
          <label>Reporter</label>
          <input
            type="text"
            name="reporter"
            value={issue.reporter}
            onChange={handleChange}
          />
          <label>Reported Date</label>
          <input
            type="text"
            name="reportedDate"
            value={issue.reportedDate}
            disabled // 날짜는 수정 불가능
          />
          <label>Priority</label>
          <select
            name="priority"
            value={issue.priority}
            onChange={handleChange}
          >
            <option value="major">Major</option>
            <option value="blocker">Blocker</option>
            <option value="critical">Critical</option>
            <option value="minor">Minor</option>
            <option value="trivial">Trivial</option>
          </select>
          <label>Assignee</label>
          <input
            type="text"
            name="assignee"
            value={issue.assignee}
            onChange={handleChange}
          />
          <label>Fixer</label>
          <input
            type="text"
            name="fixer"
            value={issue.fixer}
            onChange={handleChange}
          />
          <button onClick={() => console.log('Issue closed')}>Close Issue</button>
          <button onClick={() => console.log('Issue resolved')}>Resolve Issue</button>
          <button type="submit" onClick={handleSubmit}>Edit</button>
        </div>
      </div>
      <div>
        <div className="comments-container">
          <h3>Add a Comment</h3>
          <textarea
            name="newComment"
            value={issue.newComment}
            placeholder='Add your comment here...'
            onChange={handleChange}
          />
          <button onClick={addComment}>Comment</button>
          <ul>
            {issue.comments.map((comment, index) => (
              <li key={index}>{comment}</li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
}

export default EditIssue;