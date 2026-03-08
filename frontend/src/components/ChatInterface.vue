<script setup>
import { ref } from 'vue';
import axios from 'axios';

const query = ref('');
const messages = ref([
  { role: 'system', content: 'Hello! I am your Enterprise Knowledge Assistant. Ask me anything about your uploaded documents.' },
]);
const isLoading = ref(false);

const sendMessage = async () => {
  if (!query.value.trim()) return;

  const userQuery = query.value;
  messages.value.push({ role: 'user', content: userQuery });
  query.value = '';
  isLoading.value = true;

  try {
    const response = await axios.post('http://localhost:8080/api/chat', { query: userQuery });
    messages.value.push({ role: 'assistant', content: response.data.response });
  } catch (error) {
    console.error(error);
    messages.value.push({ role: 'assistant', content: 'Error: Could not retrieve answer. component backend.' });
  } finally {
    isLoading.value = false;
  }
};
</script>

<template>
  <div class="bg-white rounded-lg shadow-md flex flex-col h-[600px]">
    <div class="p-4 border-b border-gray-200">
      <h2 class="text-xl font-bold text-gray-800">Chat Assistant</h2>
    </div>
    
    <div class="flex-1 overflow-y-auto p-4 space-y-4 bg-gray-50">
      <div 
        v-for="(msg, index) in messages" 
        :key="index"
        :class="['flex', msg.role === 'user' ? 'justify-end' : 'justify-start']"
      >
        <div 
          :class="[
            'max-w-[80%] rounded-lg p-3',
            msg.role === 'user' ? 'bg-blue-600 text-white' : 'bg-white border border-gray-200 text-gray-800 shadow-sm'
          ]"
        >
          {{ msg.content }}
        </div>
      </div>
      <div v-if="isLoading" class="flex justify-start">
        <div class="bg-gray-200 rounded-lg p-3 text-gray-500 animate-pulse">Thinking...</div>
      </div>
    </div>

    <div class="p-4 border-t border-gray-200 bg-white rounded-b-lg">
      <div class="flex space-x-2">
        <input 
          v-model="query"
          @keyup.enter="sendMessage"
          type="text" 
          placeholder="Ask a question..." 
          class="flex-1 border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <button 
          @click="sendMessage"
          :disabled="isLoading"
          class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 disabled:opacity-50 transition"
        >
          Send
        </button>
      </div>
    </div>
  </div>
</template>
