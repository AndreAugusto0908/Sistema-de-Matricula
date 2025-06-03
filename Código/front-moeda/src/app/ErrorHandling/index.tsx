import toast from "react-hot-toast";


const handleError = (error: any): void => {
    const keys = Object.keys(error.response.data);
    for(const erro of keys) {
      toast.error(error.response.data[erro]);
    }
}


export default handleError;