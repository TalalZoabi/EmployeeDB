

const updateEmployee = async (data) => {
    const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
    const EMPLOYEE_ENDPOINT = import.meta.env.VITE_API_EMPLOYEE_ENDPOINT;
    
    const id = data.id;

    const endpoint = `${API_BASE_URL}${EMPLOYEE_ENDPOINT}/${id}`;
    
    console.log('Updating employee:', data);

    const response = await fetch(endpoint, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        console.error('Failed to update employee:', response);
        throw new Error('Failed to update employee');
    }

    return await response.json();
}

export default updateEmployee;
