import { useEffect, useState } from 'react';
import TruckList from '../components/TruckList'
import TruckSearch from '../components/TruckSearch';
import TruckSort from '../components/TruckSort';

const FleetContainer = () => {

    // UseStates
    const [trucks, setTrucks] = useState([]);
    const [searchValue, setSearchValue] = useState("");
    const [sortValue, setSortValue] = useState("");

   // Fetch Requests
    const loadTrucks = async () => {
        const response = await fetch("http://localhost:8080/trucks");
        const jsonData = await response.json();
        setTrucks(jsonData);
    }

    const patchTrucks = async (truck) => {
        await fetch(`http://localhost:8080/trucks/${truck.id}/status`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(truck.availability)
        });
        await loadTrucks();
        console.log(truck.availability);
    }

   
    // UseEffects
    useEffect(() => {
        loadTrucks();        
    }, []);
    
    //Other Functions
    const filteredTrucks = trucks.filter((truck)=> {
        if(searchValue){
            return truck.name.toLowerCase().includes(searchValue.toLowerCase());
        }
        if(sortValue){
            return truck.availability.toLowerCase().includes(sortValue.toLowerCase());
        }
        return truck;
    })

    
    return ( 
        <>
            <h2 id='fleet_title'>Delivery Fleet</h2>
            <section className='filteringForms'>
                <TruckSearch setSearchValue={setSearchValue} />
                <TruckSort setSortValue={setSortValue} />
            </section>
            <TruckList trucks={filteredTrucks} patchTrucks={patchTrucks}  />
        </>
     );
}
 
export default FleetContainer;