<script setup>
import UploadDocument from './components/UploadDocument.vue';
import ChatInterface from './components/ChatInterface.vue';
</script>

<template>
  <div class="min-h-screen bg-gray-100 py-8 px-4 sm:px-6 lg:px-8">
    <div class="max-w-4xl mx-auto">
      <header class="mb-8 text-center">
        <h1 class="text-3xl font-extrabold text-gray-900">Enterprise Knowledge Nexus</h1>
        <p class="mt-2 text-gray-600">Secure RAG Platform</p>
      </header>
      
      <main>
        <UploadDocument />
        <ChatInterface />
      </main>
    </div>
  </div>
</template>
