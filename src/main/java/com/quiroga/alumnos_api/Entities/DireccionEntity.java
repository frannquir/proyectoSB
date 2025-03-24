package com.quiroga.alumnos_api.Entities;

public class DireccionEntity {

        private int id;
        private String calle;
        private int altura;
        private int alumnoId;

        public DireccionEntity() {}

        public DireccionEntity(int id, String calle, int altura, int alumnoId) {
            this.id = id;
            this.calle = calle;
            this.altura = altura;
            this.alumnoId = alumnoId;
        }
        public DireccionEntity(String calle, int altura, int alumnoId) {
            this.calle = calle;
            this.altura = altura;
            this.alumnoId = alumnoId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCalle() {
            return calle;
        }

        public void setCalle(String calle) {
            this.calle = calle;
        }

        public int getAltura() {
            return altura;
        }

        public void setAltura(int altura) {
            this.altura = altura;
        }

        public int getAlumnoId() {
            return alumnoId;
        }

        public void setAlumnoId(int alumnoId) {
            this.alumnoId = alumnoId;
        }

        @Override
        public String toString() {
            return "Direccion{" +
                    "id=" + id +
                    ", calle='" + calle + '\'' +
                    ", altura=" + altura +
                    ", alumnoId=" + alumnoId +
                    '}';
        }
    }
