package com.example.userregistrationapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

//Classe para verificar erros com log
import android.util.Log;


//Classe principal da atividade de cadastro de usuarios
public class MainActivity extends AppCompatActivity {

//    Declara os campos de entrada de dados
    private EditText editTextName, editTextCPF , editTextEmail , editTextPhone;

//    Cria objeto para interagir com o banco de dados(dao)
    private UserDao userDao;

//    Metodo chamado quanto a atividade é criada
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Define o layout da tela

//        Inicializa os campos de entrada de dados do layout
        editTextName  = findViewById(R.id.editTextName);
        editTextCPF   = findViewById(R.id.editTextCPF);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);

//        Inicializa os botoes do layout

        Button buttonSave = findViewById(R.id.editButtonSave);
        Button buttonReport = findViewById(R.id.editButtonReport);

//        Configuracao do banco de dados usando Room
        UserDatabase db = Room.databaseBuilder(getApplicationContext(),
                UserDatabase.class, "user-database").allowMainThreadQueries().build();
        userDao = db.userDao(); // Obtem uma instancia do DAO para interagir com os dados

//        Configura o botao de salvar do usuario
        buttonSave.setOnClickListener(v -> {
//            Configura que o click aconteceu
            Log.d("MainActivity" , "Botão Cadastrar Usuário clicado");

//            Coleta dados digitados pelo usuario
            String name = editTextName.getText().toString();
            String cpf  = editTextCPF.getText().toString();
            String email = editTextEmail.getText().toString();
            String phone = editTextPhone.getText().toString();


//            Verifica se os valores foram capturados corretamente
            Log.d("MainActivity" , "Nome" + name +  ",CPF" + cpf +  ",Email" + email + ",Phone" + phone  );

//            Verifica se os campos obrigatorios (nome e Cpf) foram preenchidos
            if(!name.isEmpty() && !cpf.isEmpty()) {
//                Cria objeto usuarios e insere no banco de dados
                User user = new User(name, cpf, email, phone);
                userDao.insert(user);

//                Confirmar Insercao
                Log.d("MainActivity" , "Campos Inseridos com sucesso");

                Toast.makeText(MainActivity.this, "Usuário cadastrado" , Toast.LENGTH_SHORT).show();

            }else{
//                Confirma erro na insercao
                Log.d("MainActivity" , "Erro: Campos obrigatórios vazios");

                Toast.makeText(MainActivity.this , "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show();
            }
        });

//        Configura o botao de Relatorio para abrir a tela de relatorio
        buttonReport.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this , ReportActivity.class))
                );

    }
}