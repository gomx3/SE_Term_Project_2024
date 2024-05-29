import React, { useState } from 'react';
import './statistics.css';

function Statistics() {
  const [selectedYear, setSelectedYear] = useState(new Date().getFullYear());
  const [selectedMonth, setSelectedMonth] = useState('');
  const [issueStatistics, setIssueStatistics] = useState({ totalIssues: 0, categories: {} });

  const categories = [
    'Memory Leak',
    'Crash Occurrence',
    'User Feedback',
    'Security Vulnerability',
    'Others'
  ];

  const handleYearChange = (event) => {
    setSelectedYear(parseInt(event.target.value));
    setSelectedMonth('');
    setIssueStatistics({ totalIssues: 0, categories: {} });
  };

  const handleMonthClick = async (month) => {
    setSelectedMonth(month);

    try {
      const response = await fetch('URL', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          year: selectedYear,
          month: month
        })
      });

      const data = await response.json();
      if (data.isSuccess) {
        const result = data.result;
        const issueCountByCategory = result.issueCountByCategory;

        const categoriesData = categories.reduce((acc, category, index) => {
          acc[category] = issueCountByCategory[index];
          return acc;
        }, {});

        setIssueStatistics({
          totalIssues: result.issueCount,
          categories: categoriesData
        });
      } else {
        console.error('Failed to fetch issue statistics:', data.message);
      }

    } catch (error) {
      console.error('Error fetching issue statistics:', error);
    }
  };

  return (
    <div>
      <h2>Statistics</h2>
      <div>
        <label htmlFor="year-select">Year : </label>
        <select id="year-select" value={selectedYear} onChange={handleYearChange}>
          <option value={2023}>2023</option>
          <option value={2024}>2024</option>
        </select>
      </div>
      <div className="month-buttons">
        {Array.from({ length: 12 }, (_, i) => i + 1).map((month) => (
          <button key={month} onClick={() => handleMonthClick(month)}>{month}</button>
        ))}
      </div>
      <div>
        {selectedMonth && (
          <div>
            <h3>Month : {selectedMonth}</h3>
            <p>Total Issues: {issueStatistics.totalIssues}</p>
            <p>Categories:</p>
            <ul>
              {Object.entries(issueStatistics.categories).map(([category, count]) => (
                <li key={category}>
                  {category}: {count}
                </li>
              ))}
            </ul>
          </div>
        )}
      </div>
    </div>
  );
}

export default Statistics;
