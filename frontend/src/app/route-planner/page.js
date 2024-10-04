"use client";
import { useState, useEffect } from 'react';
import { fetchRides, fetchOptimalRoute } from '../utils/api';

export default function RoutePlanner() {
  const [rideIds, setRideIds] = useState([]);
  const [waitWeight, setWaitWeight] = useState(1);
  const [walkWeight, setWalkWeight] = useState(1);
  const [route, setRoute] = useState(null);
  const [rides, setRides] = useState([]);


  useEffect(() => {
    fetchRides().then((response) => {
      setRides(response);
    }).catch((error) => {
      console.error('Error fetching rides:', error);
    });
  }, []);

  const handleRideChange = (event) => {
    const rideId = parseInt(event.target.value, 10); // Convert rideId to an integer
  
    if (rideIds.includes(rideId)) {
      setRideIds(rideIds.filter((id) => id !== rideId)); // Remove rideId if it already exists
    } else {
      setRideIds([...rideIds, rideId]); // Add rideId if it doesn't exist
    }
    console.log("rideids: " + rideIds)
  };
  

  const handleSubmit = async (event) => {
    event.preventDefault();

      fetchOptimalRoute(rideIds, waitWeight, walkWeight).then((response) => {
        console.log("response: " + response)
        setRoute(response);
      }).catch((error) => {
        console.error('Error fetching optimal route:', error);
      });

  };

  return (
    <div className="container mx-auto">
      <h1 className="text-3xl font-bold mb-4">Route Planner</h1>
      <form onSubmit={handleSubmit} className="mb-4">
        <div>
          <label>Select rides:</label>
          {rides
  .filter((ride) => (ride.lastIsOpen)) // Apply filtering
  .map((ride) => (
    <div key={ride.id}>
      <input
        type="checkbox"
        id={ride.id}
        name={ride.rideName}
        value={ride.id}
        onChange={handleRideChange}
      />
      <label htmlFor={ride.id}>{ride.rideName}</label>
    </div>
  ))}

      
        </div>
        
        <button type="submit" className="bg-blue-500 text-white p-2 mt-4">Find Optimal Route</button>
      </form>

      {route && (
        <div>
          <h2 className="text-2xl font-bold">Optimal Route:</h2>
          <ul className="list-disc pl-5">
            {route.map((location, index) => (
              <li key={index}>{location.rideName}</li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
}
