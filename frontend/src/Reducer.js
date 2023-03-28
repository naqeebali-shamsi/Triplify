const Reducer = (state, action) => {
    switch (action.type) {
        case 'open_group':
            return {
                ...state,
                group: action.payload.group
            };
        case 'save_username':
            return {
                ...state,
                username: action.payload.username
            };
        case 'started_running':
            return {
                ...state,
                isRunning: action.payload.isRunning
            };
        case 'location_updated':
            return {
                ...state,
                latitude: action.payload.latitude,
                longitude: action.payload.longitude
            };
        case 'members_fetched':
            return {
                ...state,
                members: action.payload.members
            };
        default:
            return {
                ...state
            };
    }
};

export default Reducer;