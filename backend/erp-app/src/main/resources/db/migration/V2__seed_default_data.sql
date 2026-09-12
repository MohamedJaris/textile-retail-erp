-- 1. Currencies
INSERT INTO currencies (code, name, symbol, decimal_places, is_default) VALUES
('INR', 'Indian Rupee', '₹', 2, false),
('USD', 'US Dollar', '$', 2, false),
('EUR', 'Euro', '€', 2, false),
('GBP', 'British Pound', '£', 2, false),
('AED', 'UAE Dirham', 'د.إ', 2, false),
('SAR', 'Saudi Riyal', '﷼', 2, false);
-- NOTE: Admin should set their default currency via settings

-- 2. Tax configurations
INSERT INTO tax_configurations (name, rate, description, is_default) VALUES
('No Tax', 0.00, 'Tax exempt items', false),
('Tax 5%', 5.00, 'Standard 5% tax rate', true),
('Tax 12%', 12.00, 'Standard 12% tax rate', false),
('Tax 18%', 18.00, 'Standard 18% tax rate', false),
('Tax 28%', 28.00, 'Luxury 28% tax rate', false);

-- 3. Permissions
INSERT INTO permissions (code, description, module) VALUES 
('USER_CREATE', 'Create user', 'AUTH'),
('USER_UPDATE', 'Update user', 'AUTH'),
('USER_VIEW', 'View user', 'AUTH'),
('USER_DELETE', 'Delete user', 'AUTH'),
('ROLE_MANAGE', 'Manage roles', 'AUTH'),
('PRODUCT_CREATE', 'Create product', 'PRODUCT'),
('PRODUCT_UPDATE', 'Update product', 'PRODUCT'),
('PRODUCT_DELETE', 'Delete product', 'PRODUCT'),
('PRODUCT_VIEW', 'View product', 'PRODUCT'),
('BARCODE_GENERATE', 'Generate barcode', 'BARCODE'),
('BARCODE_REGISTER', 'Register barcode', 'BARCODE'),
('STOCK_VIEW', 'View stock', 'INVENTORY'),
('STOCK_ADJUST', 'Adjust stock', 'INVENTORY'),
('STOCK_COUNT', 'Stock count', 'INVENTORY'),
('SALE_CREATE', 'Create sale', 'SALES'),
('SALE_VIEW', 'View sale', 'SALES'),
('SALE_CANCEL', 'Cancel sale', 'SALES'),
('PRICE_OVERRIDE', 'Override price', 'SALES'),
('DISCOUNT_OVERRIDE', 'Override discount', 'SALES'),
('PURCHASE_CREATE', 'Create purchase', 'PURCHASE'),
('PURCHASE_VIEW', 'View purchase', 'PURCHASE'),
('PURCHASE_CONFIRM', 'Confirm purchase', 'PURCHASE'),
('PURCHASE_CANCEL', 'Cancel purchase', 'PURCHASE'),
('CUSTOMER_CREATE', 'Create customer', 'CUSTOMER'),
('CUSTOMER_UPDATE', 'Update customer', 'CUSTOMER'),
('CUSTOMER_VIEW', 'View customer', 'CUSTOMER'),
('CUSTOMER_DELETE', 'Delete customer', 'CUSTOMER'),
('SUPPLIER_CREATE', 'Create supplier', 'SUPPLIER'),
('SUPPLIER_UPDATE', 'Update supplier', 'SUPPLIER'),
('SUPPLIER_VIEW', 'View supplier', 'SUPPLIER'),
('SUPPLIER_DELETE', 'Delete supplier', 'SUPPLIER'),
('DEALER_CREATE', 'Create dealer', 'DEALER'),
('DEALER_UPDATE', 'Update dealer', 'DEALER'),
('DEALER_VIEW', 'View dealer', 'DEALER'),
('RETURN_CREATE', 'Create return', 'RETURNS'),
('RETURN_APPROVE', 'Approve return', 'RETURNS'),
('EXCHANGE_CREATE', 'Create exchange', 'RETURNS'),
('EXPENSE_CREATE', 'Create expense', 'ACCOUNTING'),
('EXPENSE_UPDATE', 'Update expense', 'ACCOUNTING'),
('EXPENSE_VIEW', 'View expense', 'ACCOUNTING'),
('INCOME_VIEW', 'View income', 'ACCOUNTING'),
('LEDGER_VIEW', 'View ledger', 'ACCOUNTING'),
('PAYMENT_CREATE', 'Create payment', 'ACCOUNTING'),
('REPORT_VIEW', 'View report', 'REPORTS'),
('REPORT_EXPORT', 'Export report', 'REPORTS'),
('STAFF_CREATE', 'Create staff', 'STAFF'),
('STAFF_UPDATE', 'Update staff', 'STAFF'),
('STAFF_VIEW', 'View staff', 'STAFF'),
('STAFF_DELETE', 'Delete staff', 'STAFF'),
('SETTINGS_VIEW', 'View settings', 'SETTINGS'),
('SETTINGS_UPDATE', 'Update settings', 'SETTINGS');

-- 4. Roles
INSERT INTO roles (name, description, is_system) VALUES
('ADMIN', 'Full system access', true),
('MANAGER', 'Store manager with broad access', true),
('CASHIER', 'POS and basic customer operations', true),
('INVENTORY_STAFF', 'Stock management operations', true);

-- 5. Role-permission assignments
-- ADMIN gets ALL permissions
INSERT INTO role_permissions (role_id, permission_id) 
SELECT r.id, p.id FROM roles r, permissions p WHERE r.name = 'ADMIN';

-- MANAGER gets all EXCEPT ROLE_MANAGE, USER_DELETE, SETTINGS_UPDATE
INSERT INTO role_permissions (role_id, permission_id) 
SELECT r.id, p.id FROM roles r, permissions p 
WHERE r.name = 'MANAGER' 
AND p.code NOT IN ('ROLE_MANAGE', 'USER_DELETE', 'SETTINGS_UPDATE');

-- CASHIER gets specific permissions
INSERT INTO role_permissions (role_id, permission_id) 
SELECT r.id, p.id FROM roles r, permissions p 
WHERE r.name = 'CASHIER' 
AND p.code IN ('SALE_CREATE', 'SALE_VIEW', 'PRODUCT_VIEW', 'STOCK_VIEW', 'CUSTOMER_CREATE', 'CUSTOMER_VIEW', 'RETURN_CREATE', 'EXCHANGE_CREATE', 'BARCODE_REGISTER');

-- INVENTORY_STAFF gets specific permissions
INSERT INTO role_permissions (role_id, permission_id) 
SELECT r.id, p.id FROM roles r, permissions p 
WHERE r.name = 'INVENTORY_STAFF' 
AND p.code IN ('PRODUCT_VIEW', 'STOCK_VIEW', 'STOCK_ADJUST', 'STOCK_COUNT', 'PURCHASE_VIEW', 'BARCODE_GENERATE', 'BARCODE_REGISTER');

-- 6. Default admin user
INSERT INTO users (username, password_hash, full_name, email, is_active) VALUES
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'System Administrator', 'admin@retailerp.com', true);
-- Password: admin123 (BCrypt encoded)

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r WHERE u.username = 'admin' AND r.name = 'ADMIN';
