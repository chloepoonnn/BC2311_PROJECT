// coin.js

import { computed, ref, watchEffect } from "vue";
import axios from 'axios';

export default {
  name: "App",
  setup() {
    const coins = ref([]);
    const cloneCoins = ref([]);
    const search = ref('');
    
    // api call
    const retrieveCoins = async () => {
      try {
        const response = await axios.get(
          // "http://localhost:8085/crypto/api/v1/coin/market"
          "http://localhost:8082/crypto/coingecko/api/v1/market"
        );
        coins.value = response.data.slice(0,10);
      } catch (err) {
        console.log(err);
      }
    };
    
    // fetch timer, invoke backend service in every 100 seconds 
    setInterval(() => {
      retrieveCoins();
    }, 100000);
    
    // 10 items for marquee
    const tenCoins = computed(() => {
      return coins.value.slice(0, 10);
    });
    
    // cloneCoins
    watchEffect(() => {
      const dup = coins.value.slice(0, 5);
      cloneCoins.value = dup;
    });
    
    // search
    const matchedNames = computed(() => {
      return coins.value.filter((coin) => coin.id.includes(search.value));
    });
    
    retrieveCoins(); // initial load
    return { tenCoins, cloneCoins, matchedNames, search };
  },
};
