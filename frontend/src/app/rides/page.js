"use client";  // This ensures it's a Client Component

import { useEffect, useState } from 'react';
import { fetchRides } from '../utils/api';
import RideList from '../components/RideList';


export default function RidesPage() {
  const [rides, setRides] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchRides().then((response) => {
      setRides(response);
      setLoading(false);
    }).catch((error) => {
      console.error('Error fetching rides:', error);
      setLoading(false);
    });
  }, []);

  if (loading) return <div>Loading rides...</div>;

  return (
    <div className="container mx-auto">
      <h1 className="text-3xl font-bold">Rides</h1>
      <RideList rides={rides} />
    </div>
  );
}
