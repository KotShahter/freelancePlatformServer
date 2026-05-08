// API Base URL
const API_BASE = '/api';

// Store current user info
let currentUser = null;
let selectedOrderId = null;

// Login Form Handler
document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', handleLogin);
    }

    // Add user form
    const addUserForm = document.getElementById('addUserForm');
    if (addUserForm) {
        addUserForm.addEventListener('submit', handleAddUser);
    }
});

// Login Handler
async function handleLogin(e) {
    e.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const errorMsg = document.getElementById('errorMsg');

    try {
        const response = await fetch(`${API_BASE}/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
            credentials: 'include'
        });

        if (response.ok) {
            // Fetch user info to determine role
            const userResponse = await fetch(`${API_BASE}/users`, {
                credentials: 'include'
            });

            if (userResponse.ok) {
                const users = await userResponse.json();
                const user = users.find(u => u.username === username);

                if (user) {
                    currentUser = user;
                    localStorage.setItem('currentUser', JSON.stringify(user));

                    // Redirect based on role
                    if (user.role === 'ADMIN') {
                        window.location.href = '/admin.html';
                    } else if (user.role === 'TUTOR') {
                        window.location.href = '/tutor.html';
                    } else {
                        window.location.href = '/tutor.html'; // Default for regular users
                    }
                }
            }
        } else {
            errorMsg.textContent = 'Invalid username or password';
            errorMsg.style.display = 'block';
        }
    } catch (error) {
        // If /api/users fails (403), try to get orders to determine if tutor
        try {
            const ordersResponse = await fetch(`${API_BASE}/orders`, {
                credentials: 'include'
            });

            if (ordersResponse.ok) {
                // User can access orders, likely a tutor
                currentUser = { username, role: 'TUTOR' };
                localStorage.setItem('currentUser', JSON.stringify(currentUser));
                window.location.href = '/tutor.html';
            } else {
                errorMsg.textContent = 'Invalid username or password';
                errorMsg.style.display = 'block';
            }
        } catch (e) {
            errorMsg.textContent = 'Invalid username or password';
            errorMsg.style.display = 'block';
        }
    }
}

// Check Authentication
function checkAuth(requiredRole) {
    const stored = localStorage.getItem('currentUser');
    if (!stored) {
        window.location.href = '/';
        return;
    }

    currentUser = JSON.parse(stored);

    // Update UI with user info
    const userNameEl = document.getElementById('userName');
    if (userNameEl) {
        userNameEl.textContent = currentUser.username;
    }

    // Check role
    if (requiredRole === 'ADMIN' && currentUser.role !== 'ADMIN') {
        window.location.href = '/';
    } else if (requiredRole === 'TUTOR' && currentUser.role !== 'TUTOR') {
        window.location.href = '/';
    }
}

// Logout
function logout() {
    localStorage.removeItem('currentUser');
    window.location.href = '/';
}

// Tab Switching
function showTab(tabName) {
    const tabs = document.querySelectorAll('.tab-content');
    const buttons = document.querySelectorAll('.tab-btn');

    tabs.forEach(tab => tab.classList.remove('active'));
    buttons.forEach(btn => btn.classList.remove('active'));

    document.getElementById(tabName + 'Tab').classList.add('active');
    event.target.classList.add('active');
}

// Modal Functions
function showModal(modalId) {
    document.getElementById(modalId).classList.add('active');
}

function closeModal(modalId) {
    document.getElementById(modalId).classList.remove('active');
}

// Load Users (Admin only)
async function loadUsers() {
    try {
        const response = await fetch(`${API_BASE}/users`, {
            credentials: 'include'
        });

        if (response.ok) {
            const users = await response.json();
            const tbody = document.getElementById('usersTableBody');

            tbody.innerHTML = users.map(user => `
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.contact || 'N/A'}</td>
                    <td><span class="status-badge">${user.role}</span></td>
                    <td>
                        <button class="btn-danger" onclick="deleteUser(${user.id})">Delete</button>
                    </td>
                </tr>
            `).join('');
        }
    } catch (error) {
        console.error('Error loading users:', error);
    }
}

// Load Orders (Admin and Tutor)
async function loadOrders() {
    try {
        const response = await fetch(`${API_BASE}/orders`, {
            credentials: 'include'
        });

        if (response.ok) {
            const orders = await response.json();
            const tbody = document.getElementById('ordersTableBody');
            tbody.innerHTML = orders.map(order => `
                <tr>
                    <td>${order.id}</td>
                    <td>${order.title}</td>
                    <td>${order.uni}</td>
                    <td>${order.professor}</td>
                    <td><span class="status-badge ${order.status === 'Open' ? 'status-open' : 'status-closed'}">${order.status}</span></td>
                    <!-- ✅ CHANGED HERE -->
                    <td>${order.createdBy.contact || 'N/A'}</td>
                    <td>
                        <button class="btn-danger" onclick="deleteOrder(${order.id})">Delete</button>
                    </td>
                </tr>
            `).join('');
        }
    } catch (error) {
        console.error('Error loading orders:', error);
    }
}

// Load Tutor Orders
async function loadTutorOrders() {
    try {
        const response = await fetch(`${API_BASE}/orders`, {
            credentials: 'include'
        });

        if (response.ok) {
            const orders = await response.json();
            const openOrders = orders.filter(order => order.status === 'Open');

            const grid = document.getElementById('tutorOrdersGrid');
            if (openOrders.length === 0) {
                grid.innerHTML = '<p style="color: var(--text-secondary);">No available orders at the moment.</p>';
                return;
            }

            grid.innerHTML = openOrders.map(order => `
                <div class="order-card">
                    <h3>${order.title}</h3>
                    <div class="detail"><strong>University:</strong> ${order.uni}</div>
                    <div class="detail"><strong>Professor:</strong> ${order.professor}</div>
                    <div class="detail"><strong>Notes:</strong> ${order.notes || 'No notes'}</div>
                    <!-- ✅ CHANGED HERE -->
                    <div class="detail"><strong>Contact:</strong> ${order.createdBy.contact || 'N/A'}</div>
                    <div class="status">
                        <span class="status-badge status-open">${order.status}</span>
                    </div>
                    <button class="btn-primary" style="margin-top: 1rem;" onclick="viewOrderDetails(${order.id})">
                        View Details
                    </button>
                </div>
            `).join('');
        }
    } catch (error) {
        console.error('Error loading orders:', error);
    }
}

// View Order Details (Tutor)
async function viewOrderDetails(orderId) {
    selectedOrderId = orderId;

    try {
        const response = await fetch(`${API_BASE}/orders`, {
            credentials: 'include'
        });

        if (response.ok) {
            const orders = await response.json();
            const order = orders.find(o => o.id === orderId);

            if (order) {
                const details = document.getElementById('orderDetails');
                details.innerHTML = `
                    <div class="detail"><strong>Title:</strong> ${order.title}</div>
                    <div class="detail"><strong>University:</strong> ${order.uni}</div>
                    <div class="detail"><strong>Professor:</strong> ${order.professor}</div>
                    <div class="detail"><strong>Notes:</strong> ${order.notes || 'No notes'}</div>
                    <div class="detail"><strong>Status:</strong> <span class="status-badge status-open">${order.status}</span></div>
                    <!-- ✅ CHANGED HERE -->
                    <div class="detail"><strong>Contact:</strong> ${order.createdBy.contact || 'N/A'}</div>
                `;

                showModal('orderDetailsModal');
            }
        }
    } catch (error) {
        console.error('Error loading order details:', error);
    }
}

// Accept Order (Tutor)
async function acceptOrder() {
    if (!selectedOrderId || !currentUser) return;

    try {
        const response = await fetch(`${API_BASE}/orders?orderId=${selectedOrderId}&userId=${currentUser.id}`, {
            method: 'PATCH',
            credentials: 'include'
        });

        if (response.ok) {
            alert('Order accepted successfully!');
            closeModal('orderDetailsModal');
            loadTutorOrders();
        } else {
            alert('Failed to accept order. It may have already been taken.');
        }
    } catch (error) {
        console.error('Error accepting order:', error);
        alert('Error accepting order');
    }
}

// Add User (Admin)
async function handleAddUser(e) {
    e.preventDefault();

    const username = document.getElementById('newUsername').value;
    const contact = document.getElementById('newContact').value;
    const password = document.getElementById('newPassword').value;
    const role = document.getElementById('newUserRole').value;

    try {
        const endpoint = role === 'TUTOR'
            ? '/users/tutor'
            : role === 'ADMIN'
                ? '/users/admin'
                : '/users';

        // ✅ Build form data
        const params = new URLSearchParams({
            name: username,
            contact: contact
        });

        // ✅ Only append password if actually provided
        if (password.trim()) {
            params.append('password', password.trim());
        }

        const response = await fetch(`${API_BASE}${endpoint}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: params,
            credentials: 'include'
        });

        if (response.ok) {
            alert('User created successfully!');
            closeModal('addUserModal');
            document.getElementById('addUserForm').reset();
            loadUsers();
        } else {
            const errorText = await response.text();
            alert(`Failed to create user: ${errorText}`);
        }
    } catch (error) {
        console.error('Error creating user:', error);
        alert(`Error: ${error.message}`);
    }
}

// Delete Order (Admin)
async function deleteOrder(orderId) {
    if (!confirm('Are you sure you want to delete this order?')) return;

    try {
        const response = await fetch(`${API_BASE}/orders?id=${orderId}`, {
            method: 'DELETE',
            credentials: 'include'
        });

        if (response.ok) {
            alert('Order deleted successfully!');
            loadOrders();
        } else {
            alert('Failed to delete order');
        }
    } catch (error) {
        console.error('Error deleting order:', error);
        alert('Error deleting order');
    }
}

// Delete User (Admin) - You'll need to add this endpoint to your backend
// Delete User (Admin only)
async function deleteUser(userId) {
    // Optional: Prevent deleting yourself
    if (currentUser && currentUser.id === userId) {
        alert("You cannot delete your own account!");
        return;
    }

    if (!confirm('Are you sure you want to delete this user? This action cannot be undone.')) {
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/users?id=${userId}`, {
            method: 'DELETE',
            credentials: 'include'  // ✅ Send session cookie
        });

        if (response.ok) {
            alert('User deleted successfully!');
            loadUsers();  // Refresh the table
        } else if (response.status === 404) {
            alert('User not found');
        } else if (response.status === 403) {
            alert('Permission denied. Admin access required.');
            // Optional: redirect to login if session expired
            // window.location.href = '/';
        } else {
            const errorText = await response.text();
            alert(`Failed to delete user: ${errorText}`);
        }
    } catch (error) {
        console.error('Error deleting user:', error);
        alert(`Error: ${error.message}`);
    }
}

// Close modal when clicking outside
window.onclick = function(event) {
    if (event.target.classList.contains('modal')) {
        event.target.classList.remove('active');
    }
}