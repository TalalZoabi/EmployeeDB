import React, { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import { DataGrid } from '@mui/x-data-grid';
import CircularProgress from '@mui/material/CircularProgress';
import Snackbar from '@mui/material/Snackbar';
import Button from '@mui/material/Button';
import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline';
import fetchEmployees from '../services/fetchEmployees';
import updateEmployee from '../services/updateEmployee';
import CreateEmployeeModal from './CreateEmployeeModal'; 
import ConversationStartersModal from './ConversationStartersModal'; // Import the conversation starters modal (to be implemented)

const EmployeeList = () => {
  const queryClient = useQueryClient();
  const { data: employees, error, isLoading } = useQuery('employees', fetchEmployees);
  
  const [editStatus, setEditStatus] = useState({ rowId: null, status: 'idle', error: null });
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false); 
  const [isConversationModalOpen, setIsConversationModalOpen] = useState(false);
  const [selectedEmployee, setSelectedEmployee] = useState(null);

  const mutation = useMutation(updateEmployee, {
    onSuccess: () => {
      queryClient.invalidateQueries('employees');
      setEditStatus({ rowId: null, status: 'success', error: null });
    },
    onError: (error, variables) => {
      setEditStatus({ rowId: variables.id, status: 'error', error: error.message });
    },
  });

  const handleProcessRowUpdate = async (newRow, oldRow) => {
    if (JSON.stringify(newRow) === JSON.stringify(oldRow)) {
      return oldRow; 
    }

    setEditStatus({ rowId: newRow.id, status: 'loading', error: null });

    try {
      await mutation.mutateAsync(newRow);
      return newRow;
    } catch (error) {
      return oldRow; 
    }
  };

  const handleOpenCreateModal = () => {
    setIsCreateModalOpen(true);
  };

  const handleCloseCreateModal = () => {
    setIsCreateModalOpen(false);
  };

  const handleOpenConversationModal = (employee) => {
    setSelectedEmployee(employee);
    setIsConversationModalOpen(true);
  };

  const handleCloseConversationModal = () => {
    setIsConversationModalOpen(false);
  };

  if (isLoading) return <div>Loading...</div>;
  if (error) return <div>Error: {error.message}</div>;

  const columns = [
    { field: 'id', headerName: 'ID', width: 70 },
    { field: 'firstName', headerName: 'First Name', width: 150, editable: true },
    { field: 'lastName', headerName: 'Last Name', width: 150, editable: true },
    { field: 'email', headerName: 'Email', width: 250, editable: true },
    { field: 'favoriteWikiPage', headerName: 'Favorite Wiki Page', width: 250, editable: true },
    {
      field: 'status',
      headerName: 'Status',
      width: 100,
      renderCell: (params) => {
        if (editStatus.rowId === params.id) {
          if (editStatus.status === 'loading') {
            return <CircularProgress size={24} />;
          }
          if (editStatus.status === 'error') {
            return <ErrorOutlineIcon color="error" />;
          }
        }
        return null;
      },
    },
    {
      field: 'actions',
      headerName: 'Actions',
      width: 300,
      renderCell: (params) => (
        <Button
          variant="contained"
          color="secondary"
          onClick={() => handleOpenConversationModal(params.row)}
        >
          Get Conversation Starters
        </Button>
      ),
    },
  ];

  return (
    <div style={{ height: 500, width: '100%' }}>
      <Button variant="contained" color="primary" onClick={handleOpenCreateModal}>
        Add New Employee
      </Button>
      <DataGrid
        rows={employees}
        columns={columns}
        pageSize={5}
        rowsPerPageOptions={[5, 10, 20]}
        processRowUpdate={handleProcessRowUpdate}
        experimentalFeatures={{ newEditingApi: true }}
      />
      <CreateEmployeeModal open={isCreateModalOpen} onClose={handleCloseCreateModal} />
      <ConversationStartersModal 
        open={isConversationModalOpen} 
        onClose={handleCloseConversationModal} 
        employee={selectedEmployee} 
      />
      <Snackbar
        open={editStatus.status === 'error'}
        message={`Error: ${editStatus.error}`}
        autoHideDuration={6000}
        onClose={() => setEditStatus({ ...editStatus, status: 'idle', error: null })}
      />
    </div>
  );
};

export default EmployeeList;
