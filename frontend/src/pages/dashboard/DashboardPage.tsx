import { Card, CardContent, CardHeader, CardTitle } from '../../components/ui/Card';
import { useAuthStore } from '../../stores/authStore';
import { DollarSign, Package, AlertCircle, TrendingUp, TrendingDown, Activity } from 'lucide-react';
import { formatCurrency } from '../../lib/utils';

export const DashboardPage = () => {
  const { user } = useAuthStore();

  const kpis = [
    {
      title: "Today's Sales",
      value: formatCurrency(12450.00),
      icon: DollarSign,
      trend: "+12.5%",
      positive: true,
    },
    {
      title: "Total Products",
      value: "1,245",
      icon: Package,
      trend: "+4.2%",
      positive: true,
    },
    {
      title: "Low Stock Items",
      value: "28",
      icon: AlertCircle,
      trend: "-2.1%",
      positive: true, // Lower is better for this metric
    },
    {
      title: "Monthly Revenue",
      value: formatCurrency(145000.00),
      icon: Activity,
      trend: "+8.4%",
      positive: true,
    },
    {
      title: "Receivables",
      value: formatCurrency(45200.00),
      icon: TrendingUp,
      trend: "+1.2%",
      positive: false, // Higher receivables isn't necessarily good
    },
    {
      title: "Payables",
      value: formatCurrency(28500.00),
      icon: TrendingDown,
      trend: "-5.4%",
      positive: true, // Lower payables is good
    },
  ];

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold tracking-tight text-gray-900">
            Good morning, {user?.fullName?.split(' ')[0]}
          </h1>
          <p className="text-gray-500 mt-1">
            Here's what's happening with your business today.
          </p>
        </div>
      </div>

      <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
        {kpis.map((kpi, i) => (
          <Card key={i}>
            <CardHeader className="flex flex-row items-center justify-between pb-2">
              <CardTitle className="text-sm font-medium text-gray-500">
                {kpi.title}
              </CardTitle>
              <kpi.icon className="h-4 w-4 text-gray-400" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{kpi.value}</div>
              <p className={`text-xs mt-1 font-medium ${kpi.positive ? 'text-green-600' : 'text-red-600'}`}>
                {kpi.trend} from last month
              </p>
            </CardContent>
          </Card>
        ))}
      </div>

      <div className="grid gap-6 md:grid-cols-2">
        <Card className="col-span-1 min-h-[300px] flex flex-col">
          <CardHeader>
            <CardTitle>Recent Sales</CardTitle>
          </CardHeader>
          <CardContent className="flex-1 flex items-center justify-center text-gray-500">
            Coming soon
          </CardContent>
        </Card>
        <Card className="col-span-1 min-h-[300px] flex flex-col">
          <CardHeader>
            <CardTitle>Top Selling Products</CardTitle>
          </CardHeader>
          <CardContent className="flex-1 flex items-center justify-center text-gray-500">
            Coming soon
          </CardContent>
        </Card>
      </div>
    </div>
  );
};
