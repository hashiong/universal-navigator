// RideList.js
import React, { useState } from 'react';
import RideCard from './RideCard';

const RideList = ({ rides = [] }) => {
  const [search, setSearch] = useState('');
  const [filterOpen, setFilterOpen] = useState(false);

  const filteredRides = rides
    .filter((ride) => ride.rideName.toLowerCase().includes(search.toLowerCase()))
    .filter((ride) => (filterOpen ? ride.lastIsOpen : true));

  return (
    <div className="p-4">
      <div className="flex justify-between items-center mb-4">
        <input
          type="text"
          placeholder="Search rides..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="border p-2 rounded-md shadow-md w-full max-w-lg"
        />
        <label className="ml-4 flex items-center">
          <input
            type="checkbox"
            checked={filterOpen}
            onChange={() => setFilterOpen(!filterOpen)}
            className="mr-2"
          />
          Show only open rides
        </label>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        {filteredRides.map((ride) => (
          <RideCard key={ride.id} ride={ride} />
        ))}
      </div>
    </div>
  );
};

export default RideList;
