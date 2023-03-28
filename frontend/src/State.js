import React, { useState, createContext } from "react";

export const StateContext = createContext();

export const StateProvider = props => {
  const [data, setData] = useState();

  return (
    <StateContext.Provider value={[data, setData]}>
      {props.children}
    </StateContext.Provider>
  );
};