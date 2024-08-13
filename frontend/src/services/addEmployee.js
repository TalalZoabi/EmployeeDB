

const addEmployee = async (employee) => {
    const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
    const EMPLOYEE_ENDPOINT = import.meta.env.VITE_API_EMPLOYEE_ENDPOINT;
    
    const response = await fetch(API_BASE_URL + EMPLOYEE_ENDPOINT, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(employee),
    });

    if (!response.ok) {
        console.error('Failed to add employee:', response);
        throw new Error('Failed to add employee');
    }

    return await response.json();
}

export default addEmployee;
