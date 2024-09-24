// RideCard.js
import React from 'react';

const RideCard = ({ ride }) => {
  return (
    <div className="p-4 border rounded-lg shadow-lg hover:shadow-2xl transition duration-300 bg-white">
      <h2 className="text-xl font-semibold text-gray-800">{ride.rideName}</h2>
      <p className="text-gray-600">Status: {ride.lastIsOpen ? 'Open' : 'Closed'}</p>
      <p className="text-gray-600">Wait Time: {ride.lastWaitTime} mins</p>
    </div>
  );
};

export default RideCard;
