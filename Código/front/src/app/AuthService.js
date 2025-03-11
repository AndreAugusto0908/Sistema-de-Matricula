import Cookie from 'js-cookie';

export const isTokenExpired = () => {
  const expirationTime = Cookie.get('tokenExpiration');
  if (!expirationTime) return true; // No expiration time set
  return Date.now() > parseInt(expirationTime);
};

export const getToken = () => {
  if (isTokenExpired()) {
    return null;
  }
  return Cookie.get('token'); // Return token if not expired
};

export const getUserProfile = () => {
    if (isTokenExpired()) {
        return null;
    }
    return Cookie.get('userProfile'); // Return user profile if not expired
    }

