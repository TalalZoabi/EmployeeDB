import React, { useEffect } from 'react';
import { Modal, Box, Typography, List, ListItem, CircularProgress, IconButton } from '@mui/material';
import RefreshIcon from '@mui/icons-material/Refresh';
import { useQuery } from 'react-query';
import fetchConversationStarters from '../services/fetchConversationStarters';

const ConversationStartersModal = ({ open, onClose, employee }) => {
  const { data, error, isLoading, refetch } = useQuery(
    ['conversationStarters', employee?.id],
    () => fetchConversationStarters(employee.id),
    {
      enabled: false, // Query is not automatically run
    }
  );

  useEffect(() => {
    if (open && employee?.id) {
      const abortController = new AbortController();

      refetch({ signal: abortController.signal });

      return () => {
        abortController.abort(); // Cleanup to abort the request if modal is closed
      };
    }
  }, [open, employee, refetch]);

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
        <Box display="flex" alignItems="center" justifyContent="space-between">
          <Typography variant="h6" component="h2">
            Conversation Starters for {employee?.firstName} {employee?.lastName}
          </Typography>
          <IconButton onClick={() => refetch()} disabled={isLoading}>
            <RefreshIcon />
          </IconButton>
        </Box>
        {isLoading && <CircularProgress />}
        {error && <Typography color="error">Error: {error.message}</Typography>}
        <List>
          {data?.map((starter, index) => (
            <ListItem key={index}>{starter}</ListItem>
          ))}
        </List>
      </Box>
    </Modal>
  );
};

export default ConversationStartersModal;
