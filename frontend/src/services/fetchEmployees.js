
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
const EMPLOYEE_ENDPOINT = import.meta.env.VITE_API_EMPLOYEE_ENDPOINT;


const fetchEmployees = async () => {
    const response = await fetch(API_BASE_URL + EMPLOYEE_ENDPOINT, {
        method: 'GET',
    });
    if (!response.ok) {
        console.error('Failed to fetch employees:', response);
      throw new Error('Failed to fetch employees');
    }
    return await response.json();
};


export default fetchEmployees;