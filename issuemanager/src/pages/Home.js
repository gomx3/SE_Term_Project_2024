import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './home.css';
import Projectinfo from './projectpage';

function Home() {
  const navigate = useNavigate();
  const [id, setId] = useState(1); // Hardcoded user ID
  const [memberId, setMemberId] = useState('admin'); // Hardcoded member ID
  const [role, setRole] = useState('TESTER'); // Hardcoded role
  const [error, setError] = useState('');

  const handleLogout = async () => {
    try {
      const response = await fetch('/members/logout', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({})
      });

      const data = await response.json();

      console.log('Logout Response:', data);

      if (data.isSuccess) {
        alert('Logout successful!');
        setId(null);
        setMemberId(null);
        setRole(null);
        navigate('/signin');
      } else {
        setError(data.message || 'Logout failed.');
      }
    } catch (error) {
      console.error('Logout Error:', error);
      setError('An error occurred during logout.');
    }
  };

  const [selectedProject, setSelectedProject] = useState(null);

  return (
    <div className="home-container">
      <header className="home-header">
        <h2>IssueManager</h2>
        {memberId ? (
          <div>
            Welcome, {memberId} ({role})! <button onClick={handleLogout} className="logout-link">Logout</button>
            {error && <div className="error-message">{error}</div>}
          </div>
        ) : (
          <Link to="/signin">Sign In</Link>
        )}
      </header>
      <main className="home-main">
        <Catalog role={role} setSelectedProject={setSelectedProject} />
        <Content selectedProject={selectedProject} userId={id} userRole={role} />
      </main>
    </div>
  );
}

function Catalog({ role, setSelectedProject }) {
  const [showInput, setShowInput] = useState(false);
  const [projects, setProjects] = useState([
    { id: 1, name: 'Project Alpha' },
    { id: 2, name: 'Project Beta' },
    { id: 3, name: 'Project Gamma' }
  ]);
  const [newProjectName, setNewProjectName] = useState('');

  const toggleInput = () => {
    setShowInput(!showInput);
    if (!showInput) {
      setNewProjectName('');
    }
  };

  const handleInputChange = (event) => {
    setNewProjectName(event.target.value);
  };

  const cancelInput = () => {
    toggleInput();
  };

  const addProject = async () => {
    if (newProjectName.trim() === '') {
      alert('Please enter a project name.');
      return;
    }

    const requestBody = { name: newProjectName };

    try {
      const response = await fetch('/projects', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
      });

      const data = await response.json();

      if (data.isSuccess) {
        setProjects([...projects, { id: data.result.id, name: data.result.name }]);
        toggleInput();
      } else {
        alert(data.message || 'Project addition failed.');
      }
    } catch (error) {
      console.error('Error:', error);
      alert('An error occurred while adding the project.');
    }
  };

  return (
    <aside className="home-catalog">
      <h3>Projects</h3>
      <ul>
        {projects.map((project) => (
          <li key={project.id} className="project-item" onClick={() => setSelectedProject(project)}>
            {project.name}
          </li>
        ))}
      </ul>
      {role === 'ADMIN' && (
        <>
          {showInput && (
            <div className="input-container">
              <div className="input-wrapper">
                <input type="text" value={newProjectName} onChange={handleInputChange} />
                <button className="proj-cancel" onClick={cancelInput}>Cancel</button>
                <button className="proj-add" onClick={addProject}>Add</button>
              </div>
            </div>
          )}
          {!showInput && (
            <button className="add-button" onClick={toggleInput}>Add new Project</button>
          )}
        </>
      )}
    </aside>
  );
}

function Content({ selectedProject, userId, userRole }) {
  return (
    <section className="home-content">
      <Projectinfo project={selectedProject} userId={userId} userRole={userRole} />
    </section>
  );
}

export default Home;
