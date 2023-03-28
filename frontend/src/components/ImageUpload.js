import { useState, useEffect } from "react";
import Button from "@material-ui/core/Button";
import { Box } from "@mui/material";

const ImageUpload = ({childToParent}) => {
  const [selectedImage, setSelectedImage] = useState(null);
  const [imageUrl, setImageUrl] = useState(null);

  useEffect(() => {
    if (selectedImage) {
      setImageUrl(URL.createObjectURL(selectedImage));
      childToParent(selectedImage);
    }
  }, [childToParent, selectedImage]);

  return (
    <>
      <input
        accept="image/*"
        type="file"
        id="select-image"
        style={{ display: "none" }}
        onChange={(e) => setSelectedImage(e.target.files[0])}
      />
      <label htmlFor="select-image">
        <Box textAlign='center' marginTop={2}>
        <Button variant="contained" color="primary" component="span">
          Upload
        </Button>
        </Box>
      </label>
      {imageUrl && selectedImage && (
        <Box mt={2} textAlign="center">
          <div>Image Preview:</div>
          <img src={imageUrl} alt={selectedImage.name} height="100px" />
        </Box>
      )}
    </>
  );
};

export default ImageUpload;