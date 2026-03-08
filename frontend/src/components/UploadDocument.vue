<script setup>
import { ref } from 'vue';
import axios from 'axios';

const fileInput = ref(null);
const uploadStatus = ref('');
const isUploading = ref(false);

const handleFileUpload = async () => {
  const file = fileInput.value.files[0];
  if (!file) return;

  const formData = new FormData();
  formData.append('file', file);

  isUploading.value = true;
  uploadStatus.value = 'Uploading...';

  try {
    const response = await axios.post('http://localhost:8080/api/documents/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
    uploadStatus.value = `Success! File ID: ${response.data.id}`;
  } catch (error) {
    console.error(error);
    uploadStatus.value = 'Upload failed. Ensure backend is running.';
  } finally {
    isUploading.value = false;
  }
};
</script>

<template>
  <div class="bg-white p-6 rounded-lg shadow-md mb-6">
    <h2 class="text-xl font-bold mb-4 text-gray-800">Upload Knowledge</h2>
    <div class="flex items-center space-x-4">
      <input 
        type="file" 
        ref="fileInput"
        class="block w-full text-sm text-gray-500
          file:mr-4 file:py-2 file:px-4
          file:rounded-full file:border-0
          file:text-sm file:font-semibold
          file:bg-blue-50 file:text-blue-700
          hover:file:bg-blue-100"
      />
      <button 
        @click="handleFileUpload"
        :disabled="isUploading"
        class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:opacity-50 transition"
      >
        {{ isUploading ? 'Uploading...' : 'Upload' }}
      </button>
    </div>
    <p v-if="uploadStatus" class="mt-3 text-sm text-gray-600">{{ uploadStatus }}</p>
  </div>
</template>
