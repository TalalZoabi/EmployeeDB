import React, { useState } from 'react';
import { Modal, Box, TextField, Button, Typography } from '@mui/material';
import { useMutation, useQueryClient } from 'react-query';
import addEmployee from '../services/addEmployee'; // A function to handle the POST request

const CreateEmployeeModal = ({ open, onClose }) => {
  const queryClient = useQueryClient();

  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    favoriteWikiPage: '',
  });

  const mutation = useMutation(addEmployee, {
    onSuccess: () => {
      queryClient.invalidateQueries('employees'); // Invalidate the employee list query to refetch the data
      onClose(); // Close the modal on success
    },
    onError: (error) => {
      alert(`Error: ${error.message}`); // Simple error handling
    },
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    mutation.mutate(formData); // Trigger the mutation to add the employee
  };

  return (
    <Modal open={open} onClose={onClose}>
      <Box
        sx={{
          position: 'absolute',
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
          width: 400,
          bgcolor: 'background.paper',
          border: '2px solid #000',
          boxShadow: 24,
          p: 4,
        }}
      >
        <Typography variant="h6" component="h2">
          Create New Employee
        </Typography>
        <form onSubmit={handleSubmit}>
          <TextField
            label="First Name"
            name="firstName"
            value={formData.firstName}
            onChange={handleChange}
            fullWidth
            margin="normal"
          />
          <TextField
            label="Last Name"
            name="lastName"
            value={formData.lastName}
            onChange={handleChange}
            fullWidth
            margin="normal"
          />
          <TextField
            label="Email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            fullWidth
            margin="normal"
          />
          <TextField
            label="Favorite Wiki Page"
            name="favoriteWikiPage"
            value={formData.favoriteWikiPage}
            onChange={handleChange}
            fullWidth
            margin="normal"
          />
          <Button type="submit" variant="contained" color="primary" fullWidth>
            Create Employee
          </Button>
        </form>
      </Box>
    </Modal>
  );
};

export default CreateEmployeeModal;
