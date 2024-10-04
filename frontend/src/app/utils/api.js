import axios from 'axios';

export const fetchRides = async () => {
  const res = await axios.get('http://localhost:8080/api/rides/list');
  return res.data;
};

export const fetchOptimalRoute = async (rideIds, waitWeight, walkWeight) => {
  const res = await axios.post('http://localhost:8080/api/rides/optimal-route', {
    rideIds,
    waitWeight,
    walkWeight
  });
  return res.data;
};

