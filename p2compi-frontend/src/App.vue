<template>
  <div class="main">
    <h3>
      Las paginas creadas se pueden encontrar bajo la ruta, utilizando los identificadores de las paginas y subpaginas:
      <br>
      <a href="">http://localhost/~robertob/{nombre de la pagina}/{paginas y subpaginas}</a>
    </h3>
    <h1>
      Ingresa texto de entrada:
      <br>
      <h6>Una acción a la vez, multiples acciones no son soportadas</h6>
    </h1>
    <textarea
      v-model="inputText"
      placeholder="Ingresa texto de entrada"
    ></textarea>
    <button @click="process">
      Procesar
    </button>
    <div>
      <h3>
        Mensaje de salida:
      </h3>
      
      <div v-if="restultMessage.exception" style="color: red;">
        Ha ocurrido un error, detalles:
      
        <pre>{{ restultMessage.message }}</pre>
        <pre>{{ restultMessage.exception }}</pre>
      </div>
      <div v-else-if="restultMessage.messages" style="color: green;">
        Resultado:
        <section style="padding: 1rem; border: 1px solid #ccc; border-radius: 0.25rem;" v-if="restultMessage.messages.length">
          <code v-for="message in restultMessage.messages">
            Mensaje: {{ message }}
          </code>
        </section>
        <section style="padding: 1rem; border: 1px solid #ccc; border-radius: 0.25rem;">
          <code v-for="error in restultMessage.errors">
            Error: {{ error }}
          </code>
        </section>
        <section style="color: cornflowerblue; padding: 1rem; border: 1px solid #ccc; border-radius: 0.25rem;">
          <code>
            Entrada valida: {{ restultMessage.isValid ? 'Si' : 'No'}}

          </code>
        </section>


      </div>
    </div>
  </div>
</template>
<script setup>
import { ref } from 'vue';

const inputText = ref('');
const restultMessage = ref({});

const process = async () => {
  const response = await fetch('http://localhost:8080/p2compi1_war_exploded/main', {
    method: 'POST',
    headers: {
      'Content-Type': 'text/plain',
    },
    body: inputText.value,
  });
  const result = await response.json();
  restultMessage.value = result;
  console.log(result);
}


</script>
<style scoped>
.main {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  padding-inline: 20rem;

  textarea {
    width: 700px;
    height: 400px;
    font-size: .7rem;
  }

  button {
    margin-top: 1rem;
    padding: 0.5rem 1rem;
    font-size: 1.5rem;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 0.25rem;
    cursor: pointer;
  }
}

</style>
