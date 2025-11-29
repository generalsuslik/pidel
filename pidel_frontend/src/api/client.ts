import axios from "axios";

export const getCookie = (name: string): string | undefined => {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop()?.split(';').shift();
    return undefined;
};

export const apiClient = axios.create({
    baseURL: "http://127.0.0.1:8080/api/v1/",
    headers: {
        "Content-Type": "application/json",
    },
});

apiClient.interceptors.request.use(
    (config) => {
        const token = getCookie('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

apiClient.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config;

        // If error is 401 and we haven't tried to refresh yet
        if (error.response?.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;

            try {
                const refreshResponse = await axios.post("http://127.0.0.1:8080/api/v1/auth/sign-in", {}, {
                    withCredentials: true // crucial if refresh token is in httpOnly cookie
                });

                const newToken = refreshResponse.data.token;
                document.cookie = `token=${newToken}; path=/; max-age=86400; SameSite=Lax`;

                originalRequest.headers.Authorization = `Bearer ${newToken}`;
                return apiClient(originalRequest);
            } catch (refreshError) {
                // If refresh fails, clear cookie and redirect to login
                document.cookie = 'token=; path=/; max-age=0';
                window.location.href = '/sign-in';
                return Promise.reject(refreshError);
            }
        }

        return Promise.reject(error);
    }
);
