{{/*
Expand the name of the chart.
*/}}
{{- define "precision-viticulture-platform.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "precision-viticulture-platform.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "precision-viticulture-platform.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Common labels
*/}}
{{- define "precision-viticulture-platform.labels" -}}
helm.sh/chart: {{ include "precision-viticulture-platform.chart" . }}
{{ include "precision-viticulture-platform.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "precision-viticulture-platform.selectorLabels" -}}
app.kubernetes.io/name: {{ include "precision-viticulture-platform.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{/*
Create the name of the service account to use
*/}}
{{- define "precision-viticulture-platform.serviceAccountName" -}}
{{- if .Values.serviceAccount.create }}
{{- default (include "precision-viticulture-platform.fullname" .) .Values.serviceAccount.name }}
{{- else }}
{{- default "default" .Values.serviceAccount.name }}
{{- end }}
{{- end }}

{{/*
API Gateway Full Name
*/}}
{{- define "precision-viticulture-platform.api-gateway.fullname" -}}
{{- printf "%s-api-gateway" (include "precision-viticulture-platform.fullname" .) | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Vineyard Management Full Name
*/}}
{{- define "precision-viticulture-platform.vineyard-management.fullname" -}}
{{- printf "%s-vineyard-management" (include "precision-viticulture-platform.fullname" .) | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Sensor Data Full Name
*/}}
{{- define "precision-viticulture-platform.sensor-data.fullname" -}}
{{- printf "%s-sensor-data" (include "precision-viticulture-platform.fullname" .) | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Weather Integration Full Name
*/}}
{{- define "precision-viticulture-platform.weather-integration.fullname" -}}
{{- printf "%s-weather-integration" (include "precision-viticulture-platform.fullname" .) | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Analytics Full Name
*/}}
{{- define "precision-viticulture-platform.analytics.fullname" -}}
{{- printf "%s-analytics" (include "precision-viticulture-platform.fullname" .) | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Notification Full Name
*/}}
{{- define "precision-viticulture-platform.notification.fullname" -}}
{{- printf "%s-notification" (include "precision-viticulture-platform.fullname" .) | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Market Intelligence Full Name
*/}}
{{- define "precision-viticulture-platform.market-intelligence.fullname" -}}
{{- printf "%s-market-intelligence" (include "precision-viticulture-platform.fullname" .) | trunc 63 | trimSuffix "-" }}
{{- end }}