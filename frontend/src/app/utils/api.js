import axios from 'axios';

export const fetchRides = async () => {
  const res = await axios.get('http://localhost:8080/api/rides/list');
  return res.data;
};
