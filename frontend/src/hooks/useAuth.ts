import { useAuthStore } from '../stores/authStore';
import { useLogin } from '../api/auth';

export const useAuth = () => {
  const { user, isAuthenticated, logout, hasPermission, hasRole } = useAuthStore();
  const { mutateAsync: login, isPending: isLoading } = useLogin();

  return {
    user,
    isAuthenticated,
    login,
    logout,
    hasPermission,
    hasRole,
    isLoading,
  };
};
