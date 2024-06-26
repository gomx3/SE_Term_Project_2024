import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import './home.css';
import Projectinfo from './projectpage';

function Home() {
  const navigate = useNavigate();
  const location = useLocation();
  const [id, setId] = useState(null);
  const [memberId, setMemberId] = useState('');
  const [role, setRole] = useState('');
  const [error, setError] = useState('');

  useEffect(() => {
    if (location.state) {
      setId(location.state.id);
      setMemberId(location.state.memberId);
      setRole(location.state.role);
    }
  }, [location.state]);

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
        <h1>IssueManager</h1>
        {memberId ? (
          <div>
            Welcome, <b>{memberId}</b> ({role})! <button onClick={handleLogout} className="logout-link">Logout</button>
            {error && <div className="error-message">{error}</div>}
          </div>
        ) : (
          <Link to="/signin">Sign In</Link>
        )}
      </header>
      <main className="home-main">
        <Catalog role={role} setSelectedProject={setSelectedProject} memberId={memberId} userId={id} />
        <Content selectedProject={selectedProject} userId={id} userRole={role} memberId={memberId} />
      </main>
    </div>
  );
}

function Catalog({ role, setSelectedProject, memberId, userId }) {
  const [showInput, setShowInput] = useState(false);
  const [projects, setProjects] = useState([]);
  const [newProjectName, setNewProjectName] = useState('');

  useEffect(() => {
    const fetchProjects = async () => {
      try {
        const response = await fetch(`/projects/${memberId}/check`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json'
          }
        });

        const data = await response.json();

        if (data.isSuccess) {
          const projects = data.result.projectIds.map((project) => ({
            id: project.projectId,
            name: project.projectName
          }));
          setProjects(projects);
        } else {
          console.error('Failed to fetch projects:', data.message);
        }
      } catch (error) {
        console.error('Error fetching projects:', error);
      }
    };

    fetchProjects();
  }, [userId]);

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

    const requestBody = {
      name: newProjectName,
      creatorId: memberId, 
    };

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


function Content({ selectedProject, userId, userRole, memberId }) {
  return (
    <section className="home-content">
      <Projectinfo project={selectedProject} userId={userId} userRole={userRole} memberId={memberId} />
    </section>
  );
}

export default Home;
