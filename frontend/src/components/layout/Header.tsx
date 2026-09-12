import { useLocation } from 'react-router-dom';
import { Bell, User } from 'lucide-react';
import { useAuthStore } from '../../stores/authStore';

const formatRouteName = (pathname: string) => {
  const parts = pathname.split('/').filter(Boolean);
  if (parts.length === 0) return 'Dashboard';
  return parts[0].charAt(0).toUpperCase() + parts[0].slice(1).replace(/-/g, ' ');
};

export const Header = () => {
  const location = useLocation();
  const { user } = useAuthStore();
  const title = formatRouteName(location.pathname);

  return (
    <header className="bg-white border-b border-gray-200 h-16 flex items-center justify-between px-6 sticky top-0 z-10">
      <h2 className="text-xl font-semibold text-gray-800">{title}</h2>
      
      <div className="flex items-center space-x-4">
        <button className="text-gray-500 hover:text-gray-700 relative p-2 rounded-full hover:bg-gray-100 transition-colors">
          <Bell className="w-5 h-5" />
          <span className="absolute top-1.5 right-1.5 w-2 h-2 bg-red-500 rounded-full"></span>
        </button>
        
        <div className="flex items-center space-x-2">
          <div className="w-8 h-8 rounded-full bg-primary-100 flex items-center justify-center text-primary-700 font-semibold">
            {user?.fullName?.charAt(0) || <User className="w-4 h-4" />}
          </div>
          <span className="text-sm font-medium text-gray-700 hidden sm:block">
            {user?.fullName}
          </span>
        </div>
      </div>
    </header>
  );
};
