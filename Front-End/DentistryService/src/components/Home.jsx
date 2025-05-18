import Navigation from "./Navigation";
import ProviderFilter from "../components/ProviderFilter";

const Home = () => {
  return (
    <div className="max-w-6xl mx-auto px-4 py-6 space-y-6">
      <main className="space-y-6">
        <h1 className="text-2xl font-bold border-b pb-2">List of providers</h1>
        <ProviderFilter />
      </main>
    </div>
  );
};

export default Home;
