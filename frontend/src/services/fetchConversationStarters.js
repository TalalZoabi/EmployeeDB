// services/fetchConversationStarters.js

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
const EMPLOYEE_ENDPOINT = import.meta.env.VITE_API_EMPLOYEE_ENDPOINT;

const fetchConversationStarters = async (employeeId, signal) => {

    const endpoint = `${API_BASE_URL}${EMPLOYEE_ENDPOINT}/${employeeId}/get_conversation_starters`;
    const response = await fetch(endpoint, { signal });
    if (!response.ok) {
      throw new Error('Failed to fetch conversation starters');
    }
    return await response.json();
  };
  
  export default fetchConversationStarters;
  