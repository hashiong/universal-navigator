import Link from 'next/link';
import './styles/global.css'; // Import the Tailwind CSS file

export default function HomePage() {
  return (
    <div>
      <h1>Welcome to the Theme Park Planner</h1>
      <Link href="/rides">View Rides</Link>
      <br />
      <Link href="/route-planner">Plan Your Route</Link>
    </div>
  );
}
