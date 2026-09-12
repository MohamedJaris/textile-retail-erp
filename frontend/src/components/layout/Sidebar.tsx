import { NavLink } from 'react-router-dom';
import {
  LayoutDashboard,
  Package,
  Warehouse,
  ShoppingCart,
  TruckIcon,
  Users,
  Building2,
  RotateCcw,
  UserCog,
  Calculator,
  BarChart3,
  Settings,
  LogOut
} from 'lucide-react';
import { useAuthStore } from '../../stores/authStore';
import { cn } from '../../lib/utils';

export const Sidebar = () => {
  const { user, logout } = useAuthStore();

  const navItems = [
    { name: 'Dashboard', to: '/dashboard', icon: LayoutDashboard },
    { name: 'Products', to: '/products', icon: Package, disabled: true },
    { name: 'Inventory', to: '/inventory', icon: Warehouse, disabled: true },
    { name: 'POS / Billing', to: '/pos', icon: ShoppingCart, disabled: true },
    { name: 'Purchases', to: '/purchases', icon: TruckIcon, disabled: true },
    { name: 'Customers', to: '/customers', icon: Users, disabled: true },
    { name: 'Suppliers', to: '/suppliers', icon: Building2, disabled: true },
    { name: 'Returns', to: '/returns', icon: RotateCcw, disabled: true },
    { name: 'Staff', to: '/staff', icon: UserCog, disabled: true },
    { name: 'Accounting', to: '/accounting', icon: Calculator, disabled: true },
    { name: 'Reports', to: '/reports', icon: BarChart3, disabled: true },
    { name: 'Settings', to: '/settings', icon: Settings, disabled: true },
  ];

  return (
    <div className="fixed inset-y-0 left-0 w-64 bg-gray-900 text-white flex flex-col">
      <div className="flex items-center justify-center h-16 border-b border-gray-800">
        <h1 className="text-xl font-bold tracking-wider">RETAIL ERP</h1>
      </div>
      <nav className="flex-1 px-4 py-6 space-y-1 overflow-y-auto">
        {navItems.map((item) => {
          const Icon = item.icon;
          return item.disabled ? (
            <div
              key={item.name}
              className="flex items-center px-4 py-3 text-sm font-medium rounded-lg text-gray-500 cursor-not-allowed"
              title="Coming soon"
            >
              <Icon className="w-5 h-5 mr-3" />
              {item.name}
              <span className="ml-auto text-[10px] uppercase bg-gray-800 px-2 py-0.5 rounded-full">Soon</span>
            </div>
          ) : (
            <NavLink
              key={item.name}
              to={item.to}
              className={({ isActive }) =>
                cn(
                  'flex items-center px-4 py-3 text-sm font-medium rounded-lg transition-colors',
                  isActive
                    ? 'bg-primary-600 text-white'
                    : 'text-gray-300 hover:bg-gray-800 hover:text-white'
                )
              }
            >
              <Icon className="w-5 h-5 mr-3" />
              {item.name}
            </NavLink>
          );
        })}
      </nav>
      <div className="p-4 border-t border-gray-800">
        <div className="flex items-center px-4 py-3 mb-2 rounded-lg bg-gray-800">
          <div className="flex-1 min-w-0">
            <p className="text-sm font-medium text-white truncate">{user?.fullName}</p>
            <p className="text-xs text-gray-400 truncate">@{user?.username}</p>
          </div>
        </div>
        <button
          onClick={() => logout()}
          className="flex items-center w-full px-4 py-2 text-sm font-medium text-gray-300 rounded-lg hover:bg-gray-800 hover:text-white transition-colors"
        >
          <LogOut className="w-5 h-5 mr-3" />
          Logout
        </button>
      </div>
    </div>
  );
};
