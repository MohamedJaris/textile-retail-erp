import apiClient from './client';
import { LoginRequest, LoginResponse, UserProfile } from '../types/auth';
import { useMutation, useQuery } from '@tanstack/react-query';
import { useAuthStore } from '../stores/authStore';

export const loginApi = async (data: LoginRequest): Promise<LoginResponse> => {
  const response = await apiClient.post<LoginResponse>('/auth/login', data);
  return response.data;
};

export const refreshTokenApi = async (refreshToken: string): Promise<LoginResponse> => {
  const response = await apiClient.post<LoginResponse>('/auth/refresh', { refreshToken });
  return response.data;
};

export const getCurrentUser = async (): Promise<UserProfile> => {
  const response = await apiClient.get<UserProfile>('/auth/me');
  return response.data;
};

export const useLogin = () => {
  const loginAction = useAuthStore((state) => state.login);
  return useMutation({
    mutationFn: loginApi,
    onSuccess: (data) => {
      loginAction(data);
    },
  });
};

export const useCurrentUser = () => {
  const isAuthenticated = useAuthStore((state) => state.isAuthenticated);
  return useQuery({
    queryKey: ['currentUser'],
    queryFn: getCurrentUser,
    enabled: isAuthenticated,
  });
};
