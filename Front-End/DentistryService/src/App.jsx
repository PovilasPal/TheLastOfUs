import UserClientRegistrationForm from "./components/UserClientRegistrationForm"
import { Routes, Route } from "react-router";
import LoginClientPage from "./pages/LogInClientPage";

function App() {

  return (
    <Routes>
      <Route path="/users_clients" element={<UserClientRegistrationForm />} />
      <Route path="/login/client" element={<LoginClientPage />} />
    </Routes>
  )
}

export default App
